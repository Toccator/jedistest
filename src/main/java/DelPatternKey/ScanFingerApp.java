//package DelPatternKey;
//
//import FunctionInterfaceTest.Useing.Predicate;
//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
//import jedis.GetOpt.GetOpt;
//import patternClear.OperationContext;
//import patternClear.OperationFactory;
//import patternClear.PatternBasedRedisClearApp;
//import patternClear.ScanClearParam;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.ScanParams;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//public class ScanFingerApp {
//
//    private static final Map<String, Predicate<String>> PREDICATE_MAP = new HashMap<>();
//    static{
//        PREDICATE_MAP.put("default",s->!s.contains(":")&& s.length()==32);
//    }
//
//
//
//    public static void redisScan(ScanParam scanParam){
//        ScanParams scanParams = new ScanParams().match(scanParam.getSearchPattern()).count(scanParam.setSearchCount());
//        Map<String,JedisPool> jedisPools = JedisConfiguration.getInstance().getJedisPools();
//
//        ExecutorService executorService = Executors.newScheduledThreadPool(jedisPools.size());
//        List<Future<Integer>> resultFutures =  new ArrayList<>(jedisPools.size());
//
//
//
//        String model = scanParam.getModel();
//        int i = 0;
//        for (Map.Entry<String,JedisPool> entry :jedisPools.entrySet()){
//            String zsetFile = String.format("%s%s.csv",scanClearParam.getHashFileName(),i);
//            String hashFile = String.format("%s%s.csv",scanClearParam.getHashFileName(),i);
//            i++;
//            resultFutures.add(executorService.submit(OperationFactory.CreateOperation(model,
//                    OperationContext.builder().name(entry.getKey()).jedisPool(entry.getValue())
//                            .scanParams(scanParams).scanClearParam(scanClearParam)
//                            .zsetPath(zsetFile).hashPath(hashFile).build())));
//        }
//
//        for(Future<Long> resultFuture :resultFutures){
//            try{
//                System.out.println(String.format("%s",resultFuture.get()));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        executorService.shutdown();
//    }
//
//    public static void main(String[] args){
//        if(args.length <1){
//            System.out.println("canshubudui");
//            return;
//
//        }
//
//
//
//        GetOpt getOpt = new GetOpt(args,"a:b:c:d:e:f:g:h");
//        int option;
//        while ((option = getOpt.getNextOption()) != -1) {
//            switch (option) {
//                case 'a':
//                    server  = getOpt.getOptionArg();
//                    break;
//                case 'b' :
//                    serachPattern = getOpt.getOptionArg();
//                    break;
//                case 'c' :
//                    serachCount = Integer.valueOf(getOpt.getOptionArg());
//                    break;
//                case 'd':
//                    predicate = PREDICATE_MAP.get(getOpt.getOptionArg());
//                    break;
//                case 'e' :
//                    model = getOpt.getOptionArg();
//                    break;
//                case 'f':
//                    random  = true;
//                    break;
//                case 'g' :
//                    randomCount= Integer.valueOf(getOpt.getOptionArg());
//                    break;
//                case 'h':
//                    syncCount  = Integer.valueOf(getOpt.getOptionArg());
//                    break;
//            }
//        }
//
//
//        ScanParam scanParam = ScanParam.builder()
//                .predicate(predicate)
//                .random(random)
//                .searchPattern(serachPattern)
//                .hashFileName(hashFileName)
//                .model(model)
//                .randomCount(randomCount)
//                .searchCount(serachCount)
//                .syncCount(syncCount)
//                .zsetFileName(zsetFileName)
//                .build();
//        PatternBasedRedisClearApp.redisScan(scanParam);
//    }
//
//
//}
