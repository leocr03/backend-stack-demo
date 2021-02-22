package com.leocr.backendstackdemo.rabbit.conf;

import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RabbitConfigTest {

    private RabbitConfig config;

    @BeforeEach
    void setUp() {
        config = new RabbitConfig();
    }

    @Test
    void queue() {
        config.setQueue("someQueue");
        final Queue queue = config.queue();
        assertNotNull(queue);
    }

    @Test
    void exchange() {
        final TopicExchange exchange = config.exchange();
        assertNotNull(exchange);
    }

    @Test
    void binding() {
        final Queue queue = new Queue("someQueue");
        final TopicExchange exchange = new TopicExchange("someExchange");

        final Binding binding = config.binding(queue, exchange);

        assertNotNull(binding);
    }

    @Test
    void container() {
        final ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
        final MessageListenerAdapter listenerAdapter = mock(MessageListenerAdapter.class);
        config.setQueue("someQueue");

        final SimpleMessageListenerContainer container = config.container(connectionFactory, listenerAdapter);

        assertNotNull(container);
    }

    @Test
    void connectionFactory() {
        config.setPort(1000);
        final ConnectionFactory connectionFactory = config.connectionFactory();
        assertNotNull(connectionFactory);
    }

    @Test
    void listenerAdapter() {
        final RabbitService rabbitService = mock(RabbitService.class);
        final MessageListenerAdapter messageListenerAdapter = config.listenerAdapter(rabbitService);
        assertNotNull(messageListenerAdapter);
    }
}