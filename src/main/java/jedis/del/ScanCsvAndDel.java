package jedis.del;

import jedis.GetOpt.GetOpt;
import jedis.util.JedisPoolUtile;
import jedis.util.ReadProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScanCsvAndDel {
    static String  servers[] = ReadProperties.getValue("redis.server").split(",");
    static String  password = ReadProperties.getValue("redis.password");

    private static Map<String, JedisPool> jedisPools = new HashMap<String, JedisPool>();
    static {
        String firstServer = servers[0].split(":")[0];
        String firstPort = servers[0].split(":")[1];
        try (Jedis jedis = new Jedis(firstServer,Integer.valueOf(firstPort))){
            jedis.auth(password);
            String nodes = jedis.clusterNodes();
            for(String row:nodes.split("\n")){
                if(row.contains("master")){
                    String server = row.split(" ")[1].split("@")[0];
                    String[] ipAndPort = server.split(":");
                    JedisPool jedis1 = new JedisPoolUtile().getJedisPool(firstServer,Integer.valueOf(firstPort),password);
                    jedisPools.put(server,jedis1);
                }
            }

        }catch (Exception e){

        }
    }




    private static void redisscan(String path) {
        ExecutorService executorService = Executors.newScheduledThreadPool(jedisPools.size());
        for(Map.Entry<String,JedisPool> entry :jedisPools.entrySet()){
            ScanAndClear scaner = new ScanAndClear(entry.getKey(),entry.getValue(),path);
            executorService.submit(scaner);
        }

    }

    public static void main(String[] args) {
        String path = "/Users/zcy/Desktop/test.csv";
        ScanCsvAndDel.redisscan(path);

        GetOpt getOpt = new GetOpt(args, "d:h");
        int c;
        while ((c = getOpt.getNextOption()) != -1) {
            System.out.println((char)c);
            switch (c) {
                case 'd':
                    path = getOpt.getOptionArg();
                    break;
                case 'h' :
                    printHelpInfo();
                    break;
            }
        }


    }

    private static void printHelpInfo() {
        System.out.println("d: path");
    }


}
