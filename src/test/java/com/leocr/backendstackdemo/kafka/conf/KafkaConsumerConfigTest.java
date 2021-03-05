package com.leocr.backendstackdemo.kafka.conf;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class KafkaConsumerConfigTest {

    private KafkaConsumerConfig config;

    @BeforeEach
    void setUp() {
        final ConfigurationProperties configProperties = new ConfigurationProperties();
        config = new KafkaConsumerConfig(configProperties);
    }

    @Test
    void consumerFactory() {
        final ConsumerFactory<String, String> result =  config.consumerFactory();
        assertNotNull(result);
    }

    @Test
    void kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, String> result = config.kafkaListenerContainerFactory();
        assertNotNull(result);
    }
}