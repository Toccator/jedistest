package offlineV2;

import offlineV2.channel.EBankOfflineCalculation;
import org.apache.spark.sql.SparkSession;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OfflineCalculationMain {
    public static Map<String,IChannelOfflineCalculation> CHANNEL_TO_CALC_MAP = Stream.of(
            new EBankOfflineCalculation()
    ).collect(Collectors.toMap(IChannelOfflineCalculation::getName, Function.identity()));

    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("OfflineCalculation-V2").getOrCreate();
        String processingKey = args[0];
        IChannelOfflineCalculation iChannelOfflineCalculation = CHANNEL_TO_CALC_MAP.get(processingKey);
        if(iChannelOfflineCalculation == null){
            System.out.println(String.format("未找到渠道名",processingKey));
            return;
        }

        iChannelOfflineCalculation.execute(sparkSession);

    }
}
