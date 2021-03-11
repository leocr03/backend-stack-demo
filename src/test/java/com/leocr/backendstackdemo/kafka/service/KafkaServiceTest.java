package com.leocr.backendstackdemo.kafka.service;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import com.leocr.backendstackdemo.redis.model.Message;
import com.leocr.backendstackdemo.kafka.service.impl.KafkaServiceImpl;
import com.leocr.backendstackdemo.redis.repo.RedisMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaServiceTest {

    private KafkaService service;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ConfigurationProperties config;

    @Mock
    private RedisMessageRepository redisMessageRepository;

    @BeforeEach
    void setUp() {
        service = new KafkaServiceImpl(kafkaTemplate, config, redisMessageRepository);
    }

    @Test
    void produce() {
        final Integer value = 5;
        when(config.getKafkaTopicName()).thenReturn("first");

        final String res = service.produce(value);

        verify(kafkaTemplate).send(eq("first"), eq("5"));
        assertEquals("5", res);
    }

    @Test
    void consume() {
        final String message = "23";

        service.consume(message);

        verify(config).getKafkaTopicName();
        verify(config).getKafkaTopicGroupId();
        verify(redisMessageRepository).save(any(Message.class));
    }

    @Test
    void list() {
        when(redisMessageRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(new Message(1));
            add(new Message(2));
            add(new Message(3));
            add(new Message(4));
            add(new Message(5));
        }});

        final Set<String> result = service.list();

        assertEquals(Arrays.stream(new String[]{"1", "2", "3", "4", "5"}).collect(toSet()), result);
        verify(redisMessageRepository).findAll();
    }
}