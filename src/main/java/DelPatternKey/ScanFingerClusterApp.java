package DelPatternKey;

import FunctionInterfaceTest.Useing.Predicate;
import jedis.GetOpt.GetOpt;
import jedis.util.JedisPoolUtile;
import jedis.util.ReadProperties;
import patternClear.PatternBasedRedisClearApp;
import patternClear.ScanClearParam;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ScanFingerClusterApp {
    static String  servers[] = ReadProperties.getValue("redis.server").split(",");
    static String  password = ReadProperties.getValue("redis.password");

    private static Map<String, JedisPool> jedisPools = new HashMap<>();

    static {
        String firstSever = servers[0].split(":")[0];
        String firstPort = servers[1].split(":")[1];
        try {
            Jedis jedis = new Jedis(firstSever,Integer.valueOf(firstPort));
            jedis.auth(password);
            String nodes = jedis.clusterNodes();
            for(String row :nodes.split("\n")){
                if(row.contains("master")){
                    String serverId = row.split(" ")[1].split("@")[0];
                    String[] ipAndPort = serverId.split(":");
                    JedisPool jedis1 = new JedisPoolUtile().getJedisPool(ipAndPort[0],Integer.valueOf(ipAndPort[1]),password);
                    jedisPools.put(serverId,jedis1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void redisScan(String path){
        ExecutorService executorService = Executors.newScheduledThreadPool(jedisPools.size());
        List<Future<Integer>> resultFutures =  new ArrayList<>(jedisPools.size());
        for (Map.Entry<String,JedisPool> entry :jedisPools.entrySet()){
        //    ScanFingerClear scanFingerClear = new ScanFingerClear(entry.getKey(),entry.getValue(),path);
        //    resultFutures.add(executorService.submit(scanFingerClear));
        }



        for(Future<Integer> resultFuture :resultFutures){
            try{
                System.out.println(String.format("%s",resultFuture.get()));
            } catch (InterruptedException e) {
                System.out.println(String.format("%s",e.getMessage()));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    public static void main(String[] args){
        if(args.length <1){
            System.out.println("canshubudui");
            return;

        }

        String path  = "/user/zcy/test.csv";
        GetOpt getOpt = new GetOpt(args,"p:h");
        int option;
        while ((option = getOpt.getNextOption()) != -1) {
            switch (option) {
                case 'a':
                    path  = getOpt.getOptionArg();
                    break;
                case 'h':
                    System.exit(0);

            }
        }

        ScanFingerClusterApp.redisScan(path);

    }

}
