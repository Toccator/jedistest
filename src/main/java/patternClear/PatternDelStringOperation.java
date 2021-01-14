package patternClear;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PatternDelStringOperation implements IOperation {
    private JedisPool jedisPool;
    private String name;
    private ScanParams scanParams;
    private ScanClearParam scanClearParam;
    private String hashPath;
    private String zsetpath;


    @Override
    public void setOperationContext(OperationContext operationContext) {
        this.hashPath= operationContext.getHashPath();
        this.jedisPool = operationContext.getJedisPool();
        this.name = operationContext.getName();
        this.scanClearParam = operationContext.getScanClearParam();



//        this.

    }

    @Override
    public Long call() throws Exception {
        int totalCount = 0;
        int scanCount = 0;
        Long totalSuccessCount = 0L;
        try (   Jedis jedis = jedisPool.getResource();
                BufferedWriter typeAndData = getBufferedWriter(zsetpath);
                BufferedWriter typeAndData2 = getBufferedWriter(zsetpath);){
            String cursor ="0";
            ScanResult<String> scanResult = null;
            do{
                scanResult = jedis.scan(cursor,scanParams);
                List<String> resultList = scanResult.getResult();
                scanCount++;
                for (String result:resultList){
                    String keytype = jedis.type(result);
                    if ("zset".equals(keytype)){
                        totalCount++;
                        typeAndData.write(result+"zset"+System.lineSeparator());
                        typeAndData.flush();
                        Set<String> values = jedis.zrevrange(result,0,0);
                        List<String> alist = new ArrayList<>();
                        alist.addAll(values);
                        if(alist.size()!=0){
                            String i = alist.get(0);
                            jedis.del(result);
                            jedis.set(result,i);
                        }

                    }
                }
                cursor = scanResult.getStringCursor();

            }while (!reachRandomCondition(totalCount)&&!"0".equals(scanResult.getStringCursor()));

        }
        return totalSuccessCount;
    }

    private BufferedWriter getBufferedWriter(String filePath) throws IOException {
        return new BufferedWriter(new FileWriter(filePath));
    }


    private boolean reachRandomCondition(int totalCount){
        return scanClearParam.isRandom()&&totalCount>scanClearParam.getRandomCount();
    }
}
