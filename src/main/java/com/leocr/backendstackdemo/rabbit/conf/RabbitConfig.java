package com.leocr.backendstackdemo.rabbit.conf;

import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

    @Getter
    @Value(value = "${rabbit.host}")
    private String host;

    @Getter
    @Setter
    @Value(value = "${rabbit.port}")
    private Integer port;

    @Getter
    @Value(value = "${rabbit.username}")
    private String username;

    @Getter
    @Value(value = "${rabbit.password}")
    private String password;

    @Getter
    @Setter
    @Value(value = "${rabbit.queue}")
    private String queue;

    @Getter
    @Value(value = "${rabbit.exchange}")
    private String exchange;

    @Getter
    @Value(value = "${rabbit.topic.first.routing.key}")
    private String routingKey;

    @Getter
    @Value(value = "${rabbit.virtual.host}")
    private String virtualHost;

    @Bean
    @NotNull
    Queue queue() {
        return new Queue(queue, false, false, false);
    }

    @Bean
    @NotNull
    TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    @NotNull
    Binding binding(@NotNull Queue queue, @NotNull TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    @NotNull
    SimpleMessageListenerContainer container(@NotNull ConnectionFactory connectionFactory,
                                             @NotNull MessageListenerAdapter listenerAdapter) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public @NotNull ConnectionFactory connectionFactory() {
        System.out.printf("Starting RabbitMQ connection factory. host=[%s], port=[%s], username=[%s]\n", host, port,
                username);
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean
    @NotNull
    MessageListenerAdapter listenerAdapter(@NotNull RabbitService rabbitService) {
        return new MessageListenerAdapter(rabbitService, "consume");
    }

    @Bean
    public @NotNull RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
}