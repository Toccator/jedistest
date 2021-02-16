package offlineV2;

import org.apache.spark.sql.SparkSession;

public interface IComputeOperation {
    String getName();
    void executeCompute(SparkSession sparkSession);

}
