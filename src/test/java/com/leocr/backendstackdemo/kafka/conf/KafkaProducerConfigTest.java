package com.leocr.backendstackdemo.kafka.conf;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import static org.junit.jupiter.api.Assertions.*;

class KafkaProducerConfigTest {

    private KafkaProducerConfig config;

    @BeforeEach
    void setUp() {
        final ConfigurationProperties configProperties = new ConfigurationProperties();
        config = new KafkaProducerConfig(configProperties);
    }

    @Test
    void producerFactory() {
        final ProducerFactory<String, String> result = config.producerFactory();
        assertNotNull(result);
    }

    @Test
    void kafkaTemplate() {
        final KafkaTemplate<String, String> result =  config.kafkaTemplate();
        assertNotNull(result);
    }
}