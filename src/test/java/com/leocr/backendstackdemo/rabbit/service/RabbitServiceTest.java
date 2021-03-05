package com.leocr.backendstackdemo.rabbit.service;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import com.leocr.backendstackdemo.common.model.Message;
import com.leocr.backendstackdemo.mongo.repo.MongoMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RabbitServiceTest {

    private RabbitService service;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ConfigurationProperties configurationProperties;

    @Mock
    private MongoMessageRepository mongoMessageRepository;

    @BeforeEach
    void setUp() {
        service = new RabbitService(rabbitTemplate, configurationProperties, mongoMessageRepository);
    }

    @Test
    void produce() {
        final Integer value = 1;
        when(configurationProperties.getRabbitRoutingKey()).thenReturn("someRoutingKey");
        when(configurationProperties.getRabbitExchange()).thenReturn("someExchange");

        final String res = service.produce(value);

        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());
        assertEquals("1", res);
    }

    @Test
    void produceNullValueShouldThrowException() {
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.produce(null), "Expected to throw, but it didn't");
        assertTrue(ex.getMessage().contains("Argument \"value\" cannot be null."));
        verify(rabbitTemplate, never()).send(any(), any());
    }

    @Test
    void consume() {
        final String message = "1";
        service.consume(message);
        verify(mongoMessageRepository).save(any(Message.class));
    }

    @Test
    void list() {
        final List<Message> values = Arrays.asList(new Message(1), new Message(2), new Message(3), new Message(4),
                new Message(5));
        when(mongoMessageRepository.findAll()).thenReturn(values);

        final Set<String> result = service.list();

        verify(mongoMessageRepository).findAll();
        assertEquals(Arrays.stream(new String[]{"1", "2", "3", "4", "5"}).collect(toSet()), result);
    }
}