package testerConsumer;

import javax.print.DocFlavor;

public interface IKafkaConsumerConstants {
    String BOOTSTRAP_SERVERS_CONFIG = "kafka.consumer.bootstrap.servers";
    String GROUP_ID_CONFIG = "kafka.consumer.groupId";
    String TOPIC_ID_LIST_CONFIG="kafka.consumer.topics";
    String SECURITY_PROTOCOL = "kafka.security.protocol";
    String SASL_MECHANISM = "kafka.sasl.mechanism";
    String JAVA_SECURITY_AUTH_LOGIN_CONFIG_FILENAME="kafka.java.security.auth.login.config.filename";
}
