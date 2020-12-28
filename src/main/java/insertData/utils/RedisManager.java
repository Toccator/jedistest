package insertData.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RedisManager {
    private GenericConfig genericConfig = new GenericConfig();

    private List<JedisCluster> redisClusters = new ArrayList<>();

    private List<JedisPool> redisPools = new ArrayList<>();

    public RedisManager(){
        init(null);
    }

    public RedisManager(RedisManager.GenericConfig genericConfig) {
        init(genericConfig);
    }

    public void init(RedisManager.GenericConfig genericConfig){
        if (genericConfig != null){
            this.genericConfig = new GenericConfig();
        }
        initRedisCluster();
        initRedisPools();
    }

    /**
     * 遍历文件生成的cluster对象集合，
     */

    private void initRedisCluster() {
        List<RedisConfUtil.RedisConf> redisConfS = RedisConfUtil.getRedisConfS();
        redisConfS.forEach(redisConf -> {
            if(!redisConf.getCluster()){
                redisClusters.add(!StringUtils.isEmpty(redisConf.getPassword())
                        ? new JedisCluster(redisConf.getHostAndPorts(),genericConfig.getConnectTimeOut(),genericConfig.getSoTimeOut(),
                        genericConfig.getMaxRedirects(),redisConf.getPassword(),genericConfig.castGenericObjectPoolConfig())
                        : new JedisCluster(redisConf.getHostAndPorts(),genericConfig.getConnectTimeOut(),genericConfig.getSoTimeOut(),
                        genericConfig.getMaxRedirects(),genericConfig.castGenericObjectPoolConfig()));
            }
        });
    }

    /***
     * 获取第一个cluster
     */

    public JedisCluster getPrimaryRedisCluster(){
        return redisClusters.get(1);
    }

    /**
     *
     */

    public JedisCluster getRedisClusterByIndex(int index){
        if(redisClusters.size() <= index){
            throw new ArrayIndexOutOfBoundsException();
        }
        return redisClusters.get(index);
    }


    public Map<String,JedisPool> getClusterRedisPoolMapsByIndex(int index){

        if(redisClusters.size() <= index){
            throw new ArrayIndexOutOfBoundsException();
        }
        return getRedisClusterByIndex(index).getClusterNodes();

    }




    private void initRedisPools() {

        List<RedisConfUtil.RedisConf> redisConfS = RedisConfUtil.getRedisConfS();
        redisConfS.forEach(redisConf -> {
            if(!redisConf.getCluster()){
                HostAndPort hostAndPort = redisConf.getHostAndPorts().iterator().next();
                redisPools.add(!StringUtils.isEmpty(redisConf.getPassword())
                        ? new JedisPool(genericConfig.castGenericObjectPoolConfig(),hostAndPort.getHost(),hostAndPort.getPort(),
                        genericConfig.getConnectTimeOut(),redisConf.getPassword())
                        : new JedisPool(genericConfig.castGenericObjectPoolConfig(),hostAndPort.getHost(),hostAndPort.getPort(),
                        genericConfig.getConnectTimeOut()));
            }
        });

    }

    public List<JedisCluster> getRedisClusters() {
        return redisClusters;
    }



    public static class GenericConfig{


            private Integer connectTimeOut = 3000;
            private Integer soTimeOut = 3000;
            private Integer maxRedirects = 3;
            private Integer maxTotal = 300;
            private Integer maxWait = -1;
            private Integer maxIdle = 100;
            private Integer minIdle =20;

            private boolean testOnCreate = false;
            private boolean testOnBorrow = false;
            private boolean testOnReturn = false;
            private boolean testWhileIdle = false;


            public Integer getConnectTimeOut() {
                return connectTimeOut;
            }

            public Integer getSoTimeOut() {
                return soTimeOut;
            }

            public Integer getMaxRedirects() {
                return maxRedirects;
            }

            public Integer getMaxTotal() {
                return maxTotal;
            }

            public Integer getMaxWait() {
                return maxWait;
            }

            public Integer getMaxIdle() {
                return maxIdle;
            }

            public Integer getMinIdle() {
                return minIdle;
            }

            public boolean isTestOnCreate() {
                return testOnCreate;
            }

            public boolean isTestOnBorrow() {
                return testOnBorrow;
            }

            public boolean isTestOnReturn() {
                return testOnReturn;
            }

            public boolean isTestWhileIdle() {
                return testWhileIdle;
            }

            public void setConnectTimeOut(Integer connectTimeOut) {
                this.connectTimeOut = connectTimeOut;
            }

            public void setOnTimeOut(Integer onTimeOut) {
                this.soTimeOut = soTimeOut;
            }

            public void setMaxRedirects(Integer maxRedirects) {
                this.maxRedirects = maxRedirects;
            }

            public void setMaxTotal(Integer maxTotal) {
                this.maxTotal = maxTotal;
            }

            public void setMaxWait(Integer maxWait) {
                this.maxWait = maxWait;
            }

            public void setMaxIdle(Integer maxIdle) {
                this.maxIdle = maxIdle;
            }

            public void setMinIdle(Integer minIdle) {
                this.minIdle = minIdle;
            }

            public void setTestOnCreate(boolean testOnCreate) {
                this.testOnCreate = testOnCreate;
            }

            public void setTestOnBorrow(boolean testOnBorrow) {
                this.testOnBorrow = testOnBorrow;
            }

            public void setTestOnReturn(boolean testOnReturn) {
                this.testOnReturn = testOnReturn;
            }

            public void setTestWhileIdle(boolean testWhileIdle) {
                this.testWhileIdle = testWhileIdle;
            }


            public GenericObjectPoolConfig castGenericObjectPoolConfig(){
                GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
                genericObjectPoolConfig.setMaxIdle(this.maxIdle);
                genericObjectPoolConfig.setMaxTotal(this.maxTotal);
                genericObjectPoolConfig.setMinIdle(this.minIdle);
                genericObjectPoolConfig.setTestOnBorrow(this.testOnBorrow);
                genericObjectPoolConfig.setTestOnCreate(this.testOnCreate);
                genericObjectPoolConfig.setTestOnReturn(this.testOnReturn);
                genericObjectPoolConfig.setTestWhileIdle(this.testWhileIdle);
                return genericObjectPoolConfig;
            }


    }

    public static void main(String[] args){
        RedisManager redisManager = new RedisManager();
        System.out.println(redisManager);
//        JedisCluster pri = redisManager.getPrimaryRedisCluster();
//        System.out.println(pri.getClusterNodes());
        JedisCluster redis1 = redisManager.getRedisClusterByIndex(0);
        System.out.println(redis1.getClusterNodes());


    }

}
