package testerConsumer;

import org.apache.kafka.common.TopicPartition;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisOffsetManager {
    private static String OFFSET_KEY_PRRFIX="dpko";
    private static String KEY_SEPARATOR=":";

    private Set<String> topics;
    private String groupId;

    public RedisOffsetManager(Set<String> topics,String groupId){
        this.groupId = groupId;
        this.topics = topics;
    }

    Jedis jedis = new Jedis();

    public Map<TopicPartition,Long> loadOffset(){
        Map<TopicPartition,Long> fromOffset = new HashMap<>();
        for (String topic :topics){
            String key = new StringBuilder(OFFSET_KEY_PRRFIX).append(KEY_SEPARATOR).append(groupId).append(KEY_SEPARATOR).append(topic).toString();
            Set<String> fields = jedis.hkeys(key);
            for(String field:fields){
                String value = jedis.hget(key,field);
                fromOffset.put(new TopicPartition(topic,Integer.parseInt(field)),Long.parseLong(value));
            }

        }
        return fromOffset;
    }

    public void saveOffset(String topic,int partitionId,Long offset){

        try {
            String key = new StringBuilder(OFFSET_KEY_PRRFIX).append(KEY_SEPARATOR).append(groupId).append(KEY_SEPARATOR).append(topic).toString();
            jedis.hset(key,String.valueOf(partitionId),String.valueOf(offset));

        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }



    }



}
