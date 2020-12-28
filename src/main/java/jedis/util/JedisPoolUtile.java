package jedis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtile {
    private JedisPool jedisPool = null;

    public JedisPool getJedisPool(String ip,Integer port,String password){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(32);
        jedisPoolConfig.setMaxWaitMillis(200000);
        jedisPoolConfig.setTestOnBorrow(false);
        int timeout = 300000;
        jedisPool = new JedisPool(jedisPoolConfig,ip,port,timeout,password);
        // 最大连接数, 默认8个
//        jedisPoolConfig.setMaxTotal(500);
//        //最小空闲连接数, 默认0
//        jedisPoolConfig.setMinIdle(0);
//        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
//        jedisPoolConfig.setMaxWaitMillis(200000); // 设置2秒
//        //对拿到的connection进行validateObject校验
//        jedisPoolConfig.setTestOnBorrow(true);
        return jedisPool;
    }

//    public static void main(String[] args) {
//        try (Jedis jedis = new Jedis("127.0.0.1",Integer.valueOf(7001))){
//            jedis.auth("123456");
//            JedisPool jedis1 = new JedisPoolUtile().getJedisPool("127.0.0.1",Integer.valueOf(7001),"123456");
//            System.out.println(jedis1);
//        }
//    }
}
