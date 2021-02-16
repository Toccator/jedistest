package offlineV2.redis.operation;


import com.sun.rowset.internal.Row;
import offlineV2.redis.AbstractPatternBasedRedisOperation;
import offlineV2.redis.ImportVariable;
import offlineV2.util.JedisClusterPipeline;

public class Del extends AbstractPatternBasedRedisOperation {

    private final ImportVariable keyVariable;

    public Del(String key) {
        this.keyVariable = generateVariable(key);
    }

    @Override
    public void doRedisOperation(com.mysql.cj.result.Row row, JedisClusterPipeline jedisClusterPipeline) {
        jedisClusterPipeline.del(convertedVariables(keyVariable,row));
    }
}
