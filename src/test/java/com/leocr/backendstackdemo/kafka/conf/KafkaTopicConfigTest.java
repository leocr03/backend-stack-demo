package com.leocr.backendstackdemo.kafka.conf;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaAdmin;

import static org.junit.jupiter.api.Assertions.*;

class KafkaTopicConfigTest {

    private KafkaTopicConfig config;

    @BeforeEach
    void setUp() {
        config = new KafkaTopicConfig();
    }

    @Test
    void kafkaAdmin() {
        final KafkaAdmin result = config.kafkaAdmin();
        assertNotNull(result);
    }

    @Test
    void getTopic() {
        config.setNumPartitions(1);
        config.setReplicationFactor(1);

        final NewTopic result = config.getTopic();

        assertNotNull(result);
    }
}