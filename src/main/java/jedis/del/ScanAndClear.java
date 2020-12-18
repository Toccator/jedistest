package jedis.del;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ScanAndClear implements Callable<Integer> {
    private String name;
    private JedisPool jedisPool;
    private String path;


    public ScanAndClear(String name, JedisPool jedisPool, String path) {
        this.name = name;
        this.path = path;
        this.jedisPool =jedisPool;
    }

    @Override
    public Integer call() throws Exception {
        File file = new File(path);
        int totalCount = 0;
        long successcount = 0;
        try(Pipeline pipeline = jedisPool.getResource().pipelined()) {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String line = null;
            List<String> resultList = new ArrayList<>();
            while ((line = bf.readLine())!= null){
                String split = line.trim();
                resultList.add(split);
            }
            for(String result : resultList){
//                totalCount++;
                pipeline.del(result);
                System.out.println(result);
                if(totalCount % 10000 == 0){
                    List<Object> results = pipeline.syncAndReturnAll();
                    successcount = countSuccess(results);
                    totalCount += successcount;
                }
                List<Object> results = pipeline.syncAndReturnAll();
                successcount = countSuccess(results);
                totalCount += successcount;
            }
            System.out.println("success");
            System.out.println("totalCount "+ successcount);
        }catch (Exception e){

        }
        return null;
    }

    private static long countSuccess(List<Object> results) {
        return results.stream().filter(obj -> obj instanceof Long)
                .map(obj -> (Long)obj).filter(obj -> obj ==1l).count();
    }
}
