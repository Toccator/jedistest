//package patternClear;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.ScanParams;
//
//public class PatternDelStringOperation implements IOperation {
//    private JedisPool jedisPool;
//    private String name;
//    private ScanParams scanParams;
//    private ScanClearParam scanClearParam;
//    private String hashPath;
//    private String zsetpath;
//
//
//    @Override
//    public void setOperationContext(OperationContext operationContext) {
//        this.hashPath= operationContext.getHashPath();
//        this.jedisPool = operationContext.getJedisPool();
//        this.name = operationContext.getName();
//        this.scanClearParam = operationContext.getScanClearParam();
//
//
//
////        this.
//
//    }
//
//    @Override
//    public Long call() throws Exception {
//        int totalCount = 0;
//        int totalSuccessCount = 0;
//        try {
//            Jedis jedis = jedisPool.get
//        }
//        return
//
//
//    }
//}
