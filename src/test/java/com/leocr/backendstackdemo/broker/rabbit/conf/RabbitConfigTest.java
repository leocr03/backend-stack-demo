package com.leocr.backendstackdemo.broker.rabbit.conf;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import com.leocr.backendstackdemo.common.service.BrokerService;
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
    private ConfigurationProperties configProperties;

    @BeforeEach
    void setUp() {
        config = new RabbitConfig(configProperties);
    }

    @Test
    void queue() {
        when(configProperties.getRabbitQueue()).thenReturn("someQueue");

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
        when(configProperties.getRabbitQueue()).thenReturn("someQueue");

        final SimpleMessageListenerContainer container = config.container(connectionFactory, listenerAdapter);

        assertNotNull(container);
    }

    @Test
    void connectionFactory() {
        when(configProperties.getRabbitPort()).thenReturn(1000);
        final ConnectionFactory connectionFactory = config.connectionFactory();
        assertNotNull(connectionFactory);
    }

    @Test
    void listenerAdapter() {
        final BrokerService service = mock(BrokerService.class);
        final MessageListenerAdapter messageListenerAdapter = config.listenerAdapter(service);
        assertNotNull(messageListenerAdapter);
    }

    @Test
    void rabbitAdmin() {
        when(configProperties.getRabbitPort()).thenReturn(5672);
        final RabbitAdmin rabbitAdmin = config.rabbitAdmin();
        assertNotNull(rabbitAdmin);
    }
}