//package DelPatternKey;
//
//import jdk.nashorn.internal.objects.annotations.Getter;
//import jedis.util.JedisPoolUtile;
//import jedis.util.ReadProperties;
//import redis.clients.jedis.*;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.function.Supplier;
//
//
//public class JedisConfiguration {
//
//    private String[] servers;
//    private String password;
//
//    @lombok.Getter
//    private Map<String, JedisPool> jedisPools = new HashMap<>();
//    @lombok.Getter
//    private Map<Integer,JedisPool> slotToJedisPoolMap = new HashMap<>(16384);
//
//    private JedisCluster jedisCluster;
//
//    private ThreadLocal<Jedis> singleJedis;
//
//    private  final  boolean clusterMode;
//    private static final JedisConfiguration INSTANCE = new JedisConfiguration();
//
//    public static JedisConfiguration getInstance(){
//        return getInstance();
//    }
//
//    private JedisConfiguration(){
//        servers = ReadProperties.getValue("redis.server").split(",");
//        password = ReadProperties.getValue("redis.password");
//        clusterMode = (servers.length > 1);
//        final String firstSever = servers[0].split(",")[0];
//        final int firstPort = Integer.valueOf(servers[0].split(",")[1]);
//
//        try {
//            Jedis jedis = new Jedis(firstSever,firstPort);
//            jedis.auth(password);
//            if(clusterMode){
//                String nodes = jedis.clusterNodes();
//                for(String row :nodes.split("\n")){
//                    if(row.contains("master")){
//                        String[] splits = row.split(" ");
//                        String server = splits[1].split("@")[0];
//                        String slotRange = splits[splits.length - 1];
//                        String[] ipAndPort = server.split(":");
//                        JedisPool jedisPool = new JedisPoolUtile().getJedisPool(ipAndPort[0],Integer.valueOf(ipAndPort[1]),password);
//                        jedisPools.put(server,jedisPool);
//
//                        String[] ranges = slotRange.split("-");
//                        for(int i = Integer.parseInt(ranges[0]);i<= Integer.valueOf(ranges[1]);i++){
//                            slotToJedisPoolMap.put(i,jedisPool);
//                        }
//                    }
//                }
//                Set<HostAndPort> hostAndPortSets = new HashSet<>();
//
//                for (String node:servers){
//                    String[] hostAndPort = node.split(":");
//                    hostAndPortSets.add(new HostAndPort(hostAndPort[0],Integer.parseInt(hostAndPort[1])));
//                }
//                JedisPoolConfig config = new JedisPoolConfig();
//                config.setMaxIdle(10);
//                config.setMaxTotal(100);
//                config.setMinIdle(10);
//                jedisCluster = new JedisCluster(hostAndPortSets,5000,3000,10,password,config);
//            }else {
//                jedisPools.put(servers[0],getJedisPool(firstSever,firstPort,password));
//                singleJedis = ThreadLocal.withInitial(new Supplier<Jedis>() {
//                    @Override
//                    public Jedis get() {
//
//                        return null;
//                    }
//                });
//
//            }
//
//
//
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private JedisPool getJedisPool(String firstSever, int firstPort, String password) {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(10);
//        poolConfig.setMaxTotal(500);
//        poolConfig.setMaxWaitMillis(300*1000);
//        poolConfig.setMaxIdle();
//
//        poolConfig.setMaxTotal();
//        poolConfig.setTestOnBorrow();
//
//
//    }
//
//
//}
