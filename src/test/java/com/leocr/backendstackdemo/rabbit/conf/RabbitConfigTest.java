package com.leocr.backendstackdemo.rabbit.conf;

import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitConfigTest {

    private RabbitConfig config;

    @Mock
    private RabbitPropertiesConfig rabbitPropertiesConfig;

    @BeforeEach
    void setUp() {
        config = new RabbitConfig(rabbitPropertiesConfig);
    }

    @Test
    void queue() {
        when(rabbitPropertiesConfig.getQueue()).thenReturn("someQueue");

        final Queue queue = config.queue();

        assertNotNull(queue);
        final Queue expected = new Queue("someQueue", false, false, false);
        assertTrue(new ReflectionEquals(expected).matches(queue));
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
        when(rabbitPropertiesConfig.getQueue()).thenReturn("someQueue");

        final SimpleMessageListenerContainer container = config.container(connectionFactory, listenerAdapter);

        assertNotNull(container);
    }

    @Test
    void connectionFactory() {
        when(rabbitPropertiesConfig.getPort()).thenReturn(1000);
        final ConnectionFactory connectionFactory = config.connectionFactory();
        assertNotNull(connectionFactory);
    }

    @Test
    void listenerAdapter() {
        final RabbitService rabbitService = mock(RabbitService.class);
        final MessageListenerAdapter messageListenerAdapter = config.listenerAdapter(rabbitService);
        assertNotNull(messageListenerAdapter);
    }

    @Test
    void rabbitAdmin() {
        when(rabbitPropertiesConfig.getPort()).thenReturn(5672);
        final RabbitAdmin rabbitAdmin = config.rabbitAdmin();
        assertNotNull(rabbitAdmin);
    }
}