package com.leocr.backendstackdemo.kafka.conf;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private final ConfigurationProperties config;

    @Autowired
    public KafkaProducerConfig(ConfigurationProperties config) {
        this.config = config;
    }

    @Bean
    public @NotNull ProducerFactory<String, String> producerFactory() {
        final Map<String, Object> configProps = new HashMap<>();
        final String bootstrapAddress = config.getKafkaTopicBootstrapAddress();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.RETRIES_CONFIG, "3");
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15_000);
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public @NotNull KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}