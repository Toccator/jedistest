package jedis.delay;


import jedis.util.JedisPoolUtile;
import jedis.util.ReadProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;


public class TestMainForDelayQuene {
//    private static final String ADDR = "127.0.0.1";
//    private static final int PORT = 7001;
    //初始化jedis
//private static JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig(), ADDR, PORT, 10000);


    static String servers[] = ReadProperties.getValue("redis.server").split(",");
    static String password = ReadProperties.getValue("redis.password");

    static String firstServer = servers[0].split(":")[0];
    static String firstPort = servers[0].split(":")[1];

    static {
        try(Jedis jedis = new Jedis(firstServer, Integer.valueOf(firstPort))) {
            jedis.auth(password);
            JedisPool jedis1 = new JedisPoolUtile().getJedisPool(firstServer, Integer.valueOf(firstPort), password);
            Jedis jedis2 = jedis1.getResource();
        } catch (Exception e) {

        }
    }


    private static JedisPool jedisPool = new JedisPoolUtile().getJedisPool(firstServer, Integer.valueOf(firstPort), password);

    public void run(){
            productionDelayMessage();
            consumerDelayMessage();
    }


    public void productionDelayMessage() {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.auth(password);
            //延迟5秒
            Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.SECOND, 5);
            int second3later = (int) (cal1.getTimeInMillis() / 1000);
            Long orderId = jedis.zadd("OrderId", second3later, "OID0000001");
            System.out.println(new Date() + "ms:redis生成了一个订单任务：订单ID为" + "OID0000001" + "===============" + orderId);
        }
    }

    //消费者取订单
    public void consumerDelayMessage() {
        try(Jedis jedis = jedisPool.getResource()) {
//        Jedis jedis = TestMainForDelayQuene.getJedis();
            while (true) {
                Set<Tuple> items = jedis.zrangeWithScores("OrderId", 0, 1);
                if (items == null || items.isEmpty()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                    continue;
                }
                int score = (int) ((Tuple) items.toArray()[0]).getScore();
                Calendar cal = Calendar.getInstance();
                int nowSecond = (int) (cal.getTimeInMillis() / 1000);
                if (nowSecond >= score) {
                    String orderId = ((Tuple) items.toArray()[0]).getElement();
                    Long num = jedis.zrem("OrderId", orderId);
                    System.out.println(num);
                    if (num != null && num > 0) {
                        System.out.println(new Date() + "ms:redis消费了一个任务：消费的订单OrderId为" + orderId);
                    }
                }
                break;
            }
        }
    }


    public static void main(String[] args) {
        TestMainForDelayQuene appTest = new TestMainForDelayQuene();
//        appTest.productionDelayMessage();
//        appTest.consumerDelayMessage();
        appTest.run();
    }
}