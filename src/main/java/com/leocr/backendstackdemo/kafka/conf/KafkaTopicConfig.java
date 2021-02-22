package com.leocr.backendstackdemo.kafka.conf;

import lombok.Data;
import lombok.Getter;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
public class KafkaTopicConfig {

    @Getter
    @Value(value = "${kafka.topic.first.bootstrap.address:localhost:9092}")
    private String bootstrapAddress;

    @Getter
    @Value(value = "${kafka.topic.first.topic.name:topicFirst}")
    private String topicName;

    @Getter
    @Value(value = "${kafka.topic.first.group.id.first:groupFirst}")
    private String groupId;

    @Getter
    @Value(value = "${kafka.topic.first.num.partitions:1}")
    private Integer numPartitions;

    @Getter
    @Value(value = "${kafka.topic.first.replication.factor:1}")
    private Integer replicationFactor;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic getTopic() {
        return new NewTopic(topicName, numPartitions, replicationFactor.shortValue());
    }
}