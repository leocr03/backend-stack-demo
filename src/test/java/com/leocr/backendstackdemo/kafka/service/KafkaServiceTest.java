package com.leocr.backendstackdemo.kafka.service;

import com.leocr.backendstackdemo.kafka.conf.KafkaTopicConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaServiceTest {

    private KafkaService service;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private KafkaTopicConfig kafkaTopicConfig;

    @BeforeEach
    void setUp() {
        service = new KafkaService(kafkaTemplate, kafkaTopicConfig);
    }

    @Test
    void produce() throws ExecutionException, InterruptedException {
        final Integer value = 5;
        when(kafkaTopicConfig.getTopicName()).thenReturn("first");
        //noinspection rawtypes
        final ListenableFuture listener = mock(ListenableFuture.class);
        //noinspection unchecked
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(listener);

        final ListenableFuture<SendResult<String, String>> result = service.produce(value);

        assertNotNull(result);
        verify(kafkaTemplate).send(eq("first"), eq("5"));
    }

    @Test
    void consume() {
        final String message = "some Message";

        final String result = service.consume(message);

        assertEquals("some Message", result);
        verify(kafkaTopicConfig).getTopicName();
        verify(kafkaTopicConfig).getGroupId();
    }
}