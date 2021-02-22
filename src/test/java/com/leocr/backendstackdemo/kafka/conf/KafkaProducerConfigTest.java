package com.leocr.backendstackdemo.kafka.conf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import static org.junit.jupiter.api.Assertions.*;

class KafkaProducerConfigTest {

    private KafkaProducerConfig config;

    @BeforeEach
    void setUp() {
        final KafkaTopicConfig kafkaTopicConfig = new KafkaTopicConfig();
        config = new KafkaProducerConfig(kafkaTopicConfig);
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