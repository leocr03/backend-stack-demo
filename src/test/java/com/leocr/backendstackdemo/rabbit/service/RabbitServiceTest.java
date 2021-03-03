package com.leocr.backendstackdemo.rabbit.service;

import com.leocr.backendstackdemo.common.model.Message;
import com.leocr.backendstackdemo.mongo.repo.MongoMessageRepository;
import com.leocr.backendstackdemo.rabbit.conf.RabbitConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RabbitServiceTest {

    private RabbitService service;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RabbitConfig rabbitConfig;

    @Mock
    private MongoMessageRepository mongoMessageRepository;

    @BeforeEach
    void setUp() {
        service = new RabbitService(rabbitTemplate, rabbitConfig, mongoMessageRepository);
    }

    @Test
    void produce() {
        final Integer value = 1;
        when(rabbitConfig.getRoutingKey()).thenReturn("someRoutingKey");
        when(rabbitConfig.getExchange()).thenReturn("someExchange");

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

        final List<String> result = service.list();

        verify(mongoMessageRepository).findAll();
        assertEquals(Arrays.asList("1", "2", "3", "4", "5"), result);
    }
}