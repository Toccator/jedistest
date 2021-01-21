package testerConsumer.qudao;

import com.alibaba.fastjson.JSON;
import jedis.BlockingQueue.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public class AddAcountProcesser {


    public static void process(List<ConsumerRecord<String, String>> records){
        for(ConsumerRecord<String,String> record:records){
            try {
                AccountVo addAcountVo  = JSON.parseObject(record.value(),AccountVo.class);

//                XXXXXX

            }catch (Exception e){

            }
        }
    }


}
