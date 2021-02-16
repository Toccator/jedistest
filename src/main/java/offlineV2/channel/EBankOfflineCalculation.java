package offlineV2.channel;


import com.google.common.collect.ImmutableList;
import com.sun.javafx.collections.ImmutableObservableList;
import jedis.util.ReadProperties;
import lombok.extern.slf4j.Slf4j;
import offlineV2.AbstractChannelOfflineCalculation;

import offlineV2.IComputeOperation;
import offlineV2.SparkTempTable;
import offlineV2.TableNameToPathConfig;
import offlineV2.channel.ebank.EF11;
import org.apache.spark.sql.SparkSession;
import org.springframework.cglib.beans.ImmutableBean;

import java.util.Collections;
import java.util.List;

@Slf4j
public class EBankOfflineCalculation extends AbstractChannelOfflineCalculation {

    protected String hdfsBasePath= ReadProperties.getValue("hdfs.base.user");

    @Override
    protected List<IComputeOperation> getComputeOperatitons() {
        return ImmutableList.of(
                new EF11()
                );
    }


    @Override
    protected List<SparkTempTable> getSparkTempTables(){
        return ImmutableList.of(
                SparkTempTable.builder().tableName(TableNameToPathConfig.PAY.getTableName())
                .hdfsFilePaths(Collections.singletonList(hdfsBasePath+TableNameToPathConfig.EBANK_LOGIN.getTableName())).needCache(true).build()
        );
    }

//    @Override
//    protected List<IComputeOperation> getComputeOperation(){
//        return ImmutableList.of(
//                new EF11()
//        );
//    }


    @Override
    public String getName() {
        return "EBank";
    }


}
