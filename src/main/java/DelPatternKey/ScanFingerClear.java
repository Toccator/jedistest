//package DelPatternKey;
//
//import redis.clients.jedis.JedisPool;
//
//import java.io.File;
//import java.util.concurrent.Callable;
//
//public class ScanFingerClear implements Callable<Integer>{
//    private String name;
//    private JedisPool jedisPool;
//    private String path;
//
//
//
//    public ScanFingerClear(String name, JedisPool jedisPool , String path) {
//        this.name = name;
//        this.jedisPool = jedisPool ;
//        this.path = path;
//
//    }
//
//
//    @Override
//    public Integer call() throws Exception {
//        System.out.println(name);
//        File file = new File(path);
//        int totalCount = 0;
//        int totalCount = 0;
//
//
//
//        return null;
//    }
//}
