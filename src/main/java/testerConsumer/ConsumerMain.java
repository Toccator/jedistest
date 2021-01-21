package testerConsumer;

import insertData.utils.RedisManager;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.serialization.StringDeserializer;
import redis.clients.jedis.HostAndPort;
import testerConsumer.qudao.AddAcountProcesser;

import javax.security.auth.login.AppConfigurationEntry;
import java.io.FileNotFoundException;
import java.util.*;

public class ConsumerMain {

    public static void main(String[] args) throws FileNotFoundException {
        String configurationFile = args[0];
        AppConfiguration appConf = new AppConfiguration(configurationFile);
        Map<String,String> kafkaConfig = appConf.getComponentConfigMap("kafka");
        Map<String,String> redisParams = appConf.getComponentConfigMap("redis");

        Set<HostAndPort> nodes = new HashSet<>();
        String[] nodeStr = redisParams.get("redis.servers").split(",");
        String password = redisParams.get("redis.password");
        for(String node:nodeStr){
            String[] hostAndPort = node.split(":");
            nodes.add(new HostAndPort(hostAndPort[0],Integer.parseInt(hostAndPort[1])));
        }
//        CedisHelper.setHostAndPort(nodes,password);

        String[] topics = appConf.get(IKafkaConsumerConstants.TOPIC_ID_LIST_CONFIG).split(",");
        Set<String> topicSet = new HashSet<>(Arrays.asList(topics));


        final String groupId = appConf.get(IKafkaConsumerConstants.GROUP_ID_CONFIG);


        Properties kafkaParams = new Properties();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaConfig.get(IKafkaConsumerConstants.BOOTSTRAP_SERVERS_CONFIG));
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG,kafkaConfig.get(IKafkaConsumerConstants.GROUP_ID_CONFIG));
        kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaParams.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"10000");
        kafkaParams.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"60000");

        kafkaParams.put("security.protocol",kafkaConfig.get(IKafkaConsumerConstants.SECURITY_PROTOCOL));
        kafkaParams.put("sasl.mechanism",kafkaConfig.get(IKafkaConsumerConstants.SECURITY_PROTOCOL));
        kafkaParams.put("java.security.auth.login.config",kafkaConfig.get(IKafkaConsumerConstants.SECURITY_PROTOCOL));


        RedisOffsetManager offsetManager = new RedisOffsetManager(topicSet,groupId);
        Map<TopicPartition,Long> fromOffsets = offsetManager.loadOffset();


        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(kafkaParams);
        consumer.subscribe(topicSet);
        consumer.poll(1000);

        for(Map.Entry<TopicPartition,Long> entry :fromOffsets.entrySet()){
            consumer.seek(entry.getKey(),entry.getValue());
        }

        Long beginTimestamp = 0L;
        Long endTimestamp = 0L;
        Long partitionBegin = 0L;
        Long partitionEnd = 0L;

        List<Long> acctList = new ArrayList<>();
//        List<Long> pwdList = new ArrayList<>();
//        List<Long> userList = new ArrayList<>();


        try {
            while (true){
                ConsumerRecords<String,String> records = consumer.poll(3000L);
                for (TopicPartition partition :records.partitions() ){
                    List<ConsumerRecord<String,String>> partitionRecords = records.records(partition);
                    if(partition.topic().equals("XXXXXXXXXX")){
                        acctList.add(partitionRecords.get(0).timestamp());
                        AddAcountProcesser.process(partitionRecords);
                    }
                    long lastOffset = partitionRecords.get(partitionRecords.size()-1).offset();
                    //
//                    consumer.commitAsync(Collections.singletonMap(partition,new OffsetAndMetadata(lastOffset)));
                    offsetManager.saveOffset(partition.topic(),partition.partition(),lastOffset);

                }
            }


        }finally {
            consumer.close();
        }

    }
}
