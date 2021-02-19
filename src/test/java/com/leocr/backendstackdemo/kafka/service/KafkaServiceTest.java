package com.leocr.backendstackdemo.kafka.service;

import com.leocr.backendstackdemo.kafka.conf.KafkaTopicConfig;
import com.leocr.backendstackdemo.redis.model.Message;
import com.leocr.backendstackdemo.redis.repo.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;

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

    @Mock
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        service = new KafkaService(kafkaTemplate, kafkaTopicConfig, messageRepository);
    }

    @Test
    void produce() {
        final Integer value = 5;
        when(kafkaTopicConfig.getTopicName()).thenReturn("first");
        @SuppressWarnings("unchecked") final ListenableFuture<SendResult<String, String>> listener =
                mock(ListenableFuture.class);
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(listener);

        final ListenableFuture<SendResult<String, String>> result = service.produce(value);

        assertNotNull(result);
        verify(kafkaTemplate).send(eq("first"), eq("5"));
    }

    @Test
    void consume() {
        final String message = "23";

        final String result = service.consume(message);

        assertEquals("23", result);
        verify(kafkaTopicConfig).getTopicName();
        verify(kafkaTopicConfig).getGroupId();
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void list() {
        final String value = "1, 2, 3, 4, 5";
        when(messageRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(new Message(1));
            add(new Message(2));
            add(new Message(3));
            add(new Message(4));
            add(new Message(5));
        }});

        final String result = service.list();

        assertEquals("1, 2, 3, 4, 5", result);
        verify(messageRepository).findAll();
    }
}