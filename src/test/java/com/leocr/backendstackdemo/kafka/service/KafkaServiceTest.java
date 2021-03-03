package com.leocr.backendstackdemo.kafka.service;

import com.leocr.backendstackdemo.common.model.Message;
import com.leocr.backendstackdemo.kafka.conf.KafkaTopicConfig;
import com.leocr.backendstackdemo.redis.repo.RedisMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaServiceTest {

    private KafkaService service;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private KafkaTopicConfig kafkaTopicConfig;

    @Mock
    private RedisMessageRepository redisMessageRepository;

    @BeforeEach
    void setUp() {
        service = new KafkaService(kafkaTemplate, kafkaTopicConfig, redisMessageRepository);
    }

    @Test
    void produce() {
        final Integer value = 5;
        when(kafkaTopicConfig.getTopicName()).thenReturn("first");

        final String res = service.produce(value);

        verify(kafkaTemplate).send(eq("first"), eq("5"));
        assertEquals("5", res);
    }

    @Test
    void produceNullValueShouldThrowException() {
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.produce(null), "Expected to throw, but it didn't");
        assertTrue(ex.getMessage().contains("Argument \"value\" cannot be null."));
        verify(kafkaTemplate, never()).send(any(), any());
    }

    @Test
    void consume() {
        final String message = "23";

        final String result = service.consume(message);

        assertEquals("23", result);
        verify(kafkaTopicConfig).getTopicName();
        verify(kafkaTopicConfig).getGroupId();
        verify(redisMessageRepository).save(any(Message.class));
    }

    @Test
    void list() {
        when(redisMessageRepository.findAll()).thenReturn(new ArrayList<Message>() {{
            add(new Message(1));
            add(new Message(2));
            add(new Message(3));
            add(new Message(4));
            add(new Message(5));
        }});

        final List<String> result = service.list();

        assertEquals(Arrays.asList("1", "2", "3", "4", "5"), result);
        verify(redisMessageRepository).findAll();
    }
}