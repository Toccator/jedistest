package offlineV2.redis.operation;

import com.sun.rowset.internal.Row;
import offlineV2.redis.AbstractPatternBasedRedisOperation;
import offlineV2.redis.ImportVariable;
import offlineV2.util.JedisClusterPipeline;

public class Hset extends AbstractPatternBasedRedisOperation {
    private final ImportVariable keyVariable;
    private final ImportVariable fieldVariable;
    private final ImportVariable valueVariable;


    public Hset(String key,String field,String value) {
        this.keyVariable = generateVariable(key);
        this.fieldVariable = generateVariable(field);
        this.valueVariable = generateVariable(value);
    }

    @Override
    public String toString(){
        return String.format("hset(%s,%s,%s)",keyVariable,fieldVariable,valueVariable);
    }

    @Override
    public void doRedisOperation(com.mysql.cj.result.Row row, JedisClusterPipeline jedisClusterPipeline) {
        jedisClusterPipeline.hset(
                convertedVariables(keyVariable,row),
                convertedVariables(fieldVariable,row),
                convertedVariables(valueVariable,row)
        );
    }
}
