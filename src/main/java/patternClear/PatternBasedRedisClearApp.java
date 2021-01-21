package patternClear;

import FunctionInterfaceTest.Useing.Predicate;
import com.alibaba.fastjson.parser.Feature;
import jedis.GetOpt.GetOpt;
import jedis.util.JedisPoolUtile;
import jedis.util.ReadProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class PatternBasedRedisClearApp {

    private static Map<String, JedisPool> jedisPools = new HashMap<>();

    private static final Map<String, Predicate<String>> PREDICATE_MAP = new HashMap<>();

    static{
        PREDICATE_MAP.put("default",s->true);
        PREDICATE_MAP.put("context:",s -> s.split(":")[0].length()==2);
    }

    private static void initJedisPools(String server)throws Exception{
        String[] servers;
        String password;
        if(server == "local"){
            servers = ReadProperties.getValue("redis.server").split(",");
            password = ReadProperties.getValue("redis.password");
        }else {
            throw new Exception("zhaobudao ");
        }

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

    public static void redisScan(ScanClearParam scanClearParam){
        ScanParams scanParams = new ScanParams().match(scanClearParam.getSearchPattern()).count(scanClearParam.getRandomCount());
        ExecutorService executorService = Executors.newScheduledThreadPool(jedisPools.size());
        List<Future<Long>> resultFutures =  new ArrayList<>(jedisPools.size());

        if(scanClearParam.isRandom()){
            scanClearParam.setRandomCount(scanClearParam.getSearchCount()/jedisPools.size());
        }

        String model = scanClearParam.getModel();
        int i = 0;
        for (Map.Entry<String,JedisPool> entry :jedisPools.entrySet()){
            String zsetFile = String.format("%s%s.csv",scanClearParam.getHashFileName(),i);
            String hashFile = String.format("%s%s.csv",scanClearParam.getHashFileName(),i);
            i++;
            resultFutures.add(executorService.submit(OperationFactory.CreateOperation(model,
                    OperationContext.builder().name(entry.getKey()).jedisPool(entry.getValue())
                            .scanParams(scanParams).scanClearParam(scanClearParam)
                            .zsetPath(zsetFile).hashPath(hashFile).build())));
        }

        for(Future<Long> resultFuture :resultFutures){
            try{
                System.out.println(String.format("%s",resultFuture.get()));
            } catch (InterruptedException e) {
                e.printStackTrace();
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

        String server = "risk";
        String serachPattern = null;
        int serachCount = 100000;
        int syncCount = 10000;
        Predicate<String> predicate = PREDICATE_MAP.get("default");
        boolean random = false;
        String zsetFileName = "/user/zcy/";
        String hashFileName = "/user/zcy/";
        String model = "default";
        int randomCount=-1;

        GetOpt getOpt = new GetOpt(args,"a:b:c:d:e:f:g:h");
        int option;
        while ((option = getOpt.getNextOption()) != -1) {
            switch (option) {
                case 'a':
                    server  = getOpt.getOptionArg();
                    break;
                case 'b' :
                    serachPattern = getOpt.getOptionArg();
                    break;
                case 'c' :
                    serachCount = Integer.valueOf(getOpt.getOptionArg());
                    break;
                case 'd':
                    predicate = PREDICATE_MAP.get(getOpt.getOptionArg());
                    break;
                case 'e' :
                    model = getOpt.getOptionArg();
                    break;
                case 'f':
                    random  = true;
                    break;
                case 'g' :
                    randomCount= Integer.valueOf(getOpt.getOptionArg());
                    break;
                case 'h':
                    syncCount  = Integer.valueOf(getOpt.getOptionArg());
                    break;
            }
        }


        ScanClearParam scanClearParam = ScanClearParam.builder()
                .predicate(predicate)
                .random(random)
                .searchPattern(serachPattern)
                .hashFileName(hashFileName)
                .model(model)
                .randomCount(randomCount)
                .searchCount(serachCount)
                .syncCount(syncCount)
                .zsetFileName(zsetFileName)
                .build();
        PatternBasedRedisClearApp.redisScan(scanClearParam);
    }


}
