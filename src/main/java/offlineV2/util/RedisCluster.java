package offlineV2.util;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class RedisCluster implements Serializable {

    private static JedisCluster jedis;
    private static JedisClusterPipeline jcp;

    static String[] servers = ReadProperties.getValue("redis.server").split(",");
    static String password = ReadProperties.getValue("redis.server.password");


    static Set<HostAndPort> hostAndPortsSet ;
    static {
        hostAndPortsSet = new HashSet<>();
        for( String server : servers ) {
            hostAndPortsSet.add(new HostAndPort(server.split(":")[0], Integer.valueOf(server.split(":")[1])));
        }
    }

    // Jedis连接池配置
    static JedisPoolConfig jedisPoolConfig;
    static {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10000);
        jedisPoolConfig.setMaxTotal(10000);
        jedisPoolConfig.setMaxWaitMillis(300000);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(300000);

    }

    public static JedisClusterPipeline getJedisPipLine() {

        jedis = new JedisCluster(hostAndPortsSet,3000,300000,5,password, jedisPoolConfig);
        jcp = JedisClusterPipeline.pipelined(jedis);
        jcp.refreshCluster();
        return jcp;
    }

    public static JedisCluster getJedis() {

        jedis = new JedisCluster(hostAndPortsSet,3000,300000,5,password, jedisPoolConfig);
        return jedis;
    }
}
