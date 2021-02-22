package com.leocr.backendstackdemo.rabbit.service;

import com.leocr.backendstackdemo.rabbit.conf.RabbitConfig;
import com.leocr.backendstackdemo.redis.model.Message;
import com.leocr.backendstackdemo.redis.repo.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitServiceTest {

    private RabbitService service;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private RabbitConfig rabbitConfig;

    @Mock
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() {
        service = new RabbitService(rabbitTemplate, rabbitConfig, messageRepository);
    }

    @Test
    void produce() {
        final Integer value = 1;
        when(rabbitConfig.getRoutingKey()).thenReturn("someRoutingKey");
        when(rabbitConfig.getExchange()).thenReturn("someExchange");

        service.produce(value);

        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), anyString());
    }

    @Test
    void consume() {
        final String message = "1";
        service.consume(message);
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void list() {
        final Iterable<Message> values = Arrays.asList(new Message(1), new Message(2), new Message(3), new Message(4),
                new Message(5));
        when(messageRepository.findAll()).thenReturn(values);

        final String result = service.list();

        verify(messageRepository).findAll();
        assertEquals("1, 2, 3, 4, 5", result);
    }
}