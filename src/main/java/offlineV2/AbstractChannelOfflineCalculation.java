package offlineV2;

import com.sun.rowset.internal.Row;
import lombok.extern.slf4j.Slf4j;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;
import java.util.List;

@Slf4j
public abstract class AbstractChannelOfflineCalculation implements IChannelOfflineCalculation {

    @Override
    public void execute(SparkSession sparkSession){
        createSparkTempTable(sparkSession);

        List<IComputeOperation> computeOperations = getComputeOperatitons();
        for(IComputeOperation computeOperation :computeOperations){
            log.warn("");
            computeOperation.executeCompute(sparkSession);
        }

    }

    protected abstract List<IComputeOperation> getComputeOperatitons();


    private void createSparkTempTable(SparkSession sparkSession){
        List<SparkTempTable> sparkTempTables = getSparkTempTables();
        for(SparkTempTable sparkTempTable :sparkTempTables){
            String[] hdfsPaths = sparkTempTable.getHdfsFilePaths().toArray(new String[0]);
//            Dataset<Row> dataSet = sparkSession.read().option("merSchema","true").parquet(hdfsPaths);
//            dataSet.createGlobalTempView(sparkTempTable.getTableName());
//            if(sparkTempTable.isNeedCache()){
//                dataSet.cache();
//            }
            log.warn("创建spark临时表{},路径：{}，是否缓存：{}",sparkTempTable.getTableName(),Arrays.toString(hdfsPaths),Arrays.toString(hdfsPaths),sparkTempTable.isNeedCache());
        }
    }

    protected abstract List<SparkTempTable> getSparkTempTables();

}
