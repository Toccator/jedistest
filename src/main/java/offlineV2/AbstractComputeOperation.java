//package offlineV2;
//
//import com.mysql.cj.result.Row;
//import insertData.utils.RedisPipeline;
//import offlineV2.util.JedisClusterPipeline;
//import lombok.extern.slf4j.Slf4j;
//import offlineV2.redis.IRedisOperation;
//import offlineV2.util.RedisCluster;
//import org.apache.spark.api.java.function.ForeachPartitionFunction;
//import org.apache.spark.sql.SparkSession;
//
//import java.io.Serializable;
//import java.util.List;
//@Slf4j
//public abstract class AbstractComputeOperation implements IComputeOperation, Serializable {
//
//    static JedisClusterPipeline jedisClusterPipeline = RedisCluster.getJedisPipLine();
//
//    @Override
//    public String getName() {
//        return null;
//    }
//
//    @Override
//    public void executeCompute(SparkSession sparkSession) {
//        log.info("redisExec");
//        sparkSession.sql(getExecuteSql()).repartition(getRepartitionCount()).foreachPartition((ForeachPartitionFunction<Row>) iterator ->{
//            while (iterator.hasNext()){
//                Row row = iterator.next();
//                getOperations().forEach(redisOperation -> redisOperation.doRedisOperation(row,jedisClusterPipeline));
//            }
//        });
//
//
//    }
//
//    protected abstract String getExecuteSql();
//
//
//    protected abstract int getRepartitionCount();
//
//    protected abstract List<IRedisOperation> getOperations();
//
//
//}
