package offlineV2;

import org.apache.spark.sql.SparkSession;

public interface IChannelOfflineCalculation {

    String getName();

    void execute(SparkSession sparkSession);
}
