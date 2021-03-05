package com.leocr.backendstackdemo.kafka.conf;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaAdmin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaTopicConfigTest {

    private KafkaTopicConfig config;

    @Mock
    private ConfigurationProperties configProperties;

    @BeforeEach
    void setUp() {
        config = new KafkaTopicConfig(configProperties);
    }

    @Test
    void kafkaAdmin() {
        final KafkaAdmin result = config.kafkaAdmin();
        assertNotNull(result);
    }

    @Test
    void getTopic() {
        when(configProperties.getKafkaTopicNumPartitions()).thenReturn(1);
        when(configProperties.getKafkaTopicReplicationFactor()).thenReturn(1);

        final NewTopic result = config.getTopic();

        assertNotNull(result);
    }
}