package com.leocr.backendstackdemo.kafka.conf;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import lombok.Data;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
public class KafkaTopicConfig {

    private ConfigurationProperties config;

    @Autowired
    public KafkaTopicConfig(ConfigurationProperties config) {
        this.config = config;
    }

    @Bean
    public @NotNull KafkaAdmin kafkaAdmin() {
        final Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, config.getKafkaTopicBootstrapAddress());
        return new KafkaAdmin(configs);
    }

    @Bean
    public @NotNull NewTopic getTopic() {
        return new NewTopic(config.getKafkaTopicName(), config.getKafkaTopicNumPartitions(),
                config.getKafkaTopicReplicationFactor().shortValue());
    }
}