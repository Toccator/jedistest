package insertData.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedisPipeline {
    private RedisManager redisManager = null;
    private List<List<SlotAndPipeline>> allSlotAndPipelines = new ArrayList<>();
    private Pipeline pipeline = null;

    public RedisPipeline() {
        init(null);
    }

    public RedisPipeline(RedisManager.GenericConfig genericConfig) {
        init(genericConfig);
    }

    private void init(RedisManager.GenericConfig genericConfig) {
        Pattern hostAndPortPattern = Pattern.compile("([0-9]{1,3}\\.){3}([0-9]{1,3}:)[0-9]{4}");
        Pattern slotPattern = Pattern.compile("([0-9]{1,5})-([0-9]{1,5})");
        if (genericConfig == null) {
            redisManager = new RedisManager();
        } else {
            redisManager = new RedisManager(genericConfig);
        }
        final List<JedisCluster> lists = redisManager.getRedisClusters();
        lists.forEach(redisCluster -> {
            List<SlotAndPipeline> slotAndPipelines = new ArrayList<>();
            Map<String, JedisPool> clusterNodesRedisPools = redisCluster.getClusterNodes();

            JedisPool jedisPool = clusterNodesRedisPools.entrySet().iterator().next().getValue();
            Jedis jedis = jedisPool.getResource();

            String clusterNodes = jedis.clusterNodes();
            String[] nodes = clusterNodes.split("\n");

            Arrays.asList(nodes).forEach(node -> {
                Matcher slotMatcher = slotPattern.matcher(node);
                Matcher hostAndPortMatch = hostAndPortPattern.matcher(node);
                if (slotMatcher.find() && hostAndPortMatch.find()) {
                    String slot = slotMatcher.group();
                    String[] slots = slot.split("-");
                    final String hostAndPort = hostAndPortMatch.group();
                    SlotAndPipeline slotAndPipeline = new SlotAndPipeline(Integer.valueOf(slots[0]), Integer.valueOf(slots[1]), hostAndPort);
                    slotAndPipeline.setPipeline(clusterNodesRedisPools.get(hostAndPort).getResource().pipelined());
                    slotAndPipelines.add(slotAndPipeline);
                }
            });
            allSlotAndPipelines.add(slotAndPipelines);
        });
    }

    /**
     * 异步执行
     * @param slotAndPipelines
     */
    public void sync(List<SlotAndPipeline> slotAndPipelines) {
        slotAndPipelines.forEach(slotAndPipeline -> slotAndPipeline.pipeline.sync());
    }

    public void sync() {
        sync(getPrimarySlotAndPipeline());

    }

    /**
     * 获取第一个集群的pipeline
     * @return
     */
    public List<SlotAndPipeline> getPrimarySlotAndPipeline() {
        return getSlotAndPipelineByIndex(0);
    }

    public List<SlotAndPipeline> getSlotAndPipelineByIndex(int index) {
        if (allSlotAndPipelines.size() <= index) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return allSlotAndPipelines.get(index);
    }

    /**
     *根据slot获取pipeline
     * @param slot
     * @param slotAndPipelines
     * @return
     */

    public Pipeline getPipelineBySlot(int slot,List<SlotAndPipeline> slotAndPipelines){
        if(slot >16384 || slot < 0 ){
            throw new RuntimeException();
        }
        for (SlotAndPipeline slotAndPipeline : slotAndPipelines){
            Pipeline pipelineBySlot = slotAndPipeline.getPipelineBySlot(slot);
            if(null != pipelineBySlot){
                return pipelineBySlot;
            }
        }
        return null;
    }






    /**
     * SlotAndPipeline
     */

    private static class SlotAndPipeline {
        private int start;
        private int end;
        private Pipeline pipeline;
        private String hostAndPort;


        public SlotAndPipeline(int start, int end, String hostAndPort) {
            this.end = end;
            this.start = start;
            this.hostAndPort = hostAndPort;
        }

        public void setPipeline(Pipeline pipeline) {
            this.pipeline = pipeline;
        }

        public Pipeline getPipelineBySlot(int slot) {
            if (slot >= start && slot <= end) {
                return pipeline;
            }
            return null;
        }

    }
}
