package com.leocr.backendstackdemo.rabbit.conf;

import com.leocr.backendstackdemo.rabbit.service.RabbitService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

    private RabbitPropertiesConfig rabbitPropertiesConfig;

    @Autowired
    public RabbitConfig(RabbitPropertiesConfig rabbitPropertiesConfig) {
        this.rabbitPropertiesConfig = rabbitPropertiesConfig;
    }

    @Bean
    @NotNull
    Queue queue() {
        return new Queue(rabbitPropertiesConfig.getQueue(), false, false, false);
    }

    @Bean
    @NotNull
    TopicExchange exchange() {
        return new TopicExchange(rabbitPropertiesConfig.getExchange());
    }

    @Bean
    @NotNull
    Binding binding(@NotNull Queue queue, @NotNull TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(rabbitPropertiesConfig.getRoutingKey());
    }

    @Bean
    @NotNull
    SimpleMessageListenerContainer container(@NotNull ConnectionFactory connectionFactory,
                                             @NotNull MessageListenerAdapter listenerAdapter) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(rabbitPropertiesConfig.getQueue());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public @NotNull ConnectionFactory connectionFactory() {
        System.out.printf("Starting RabbitMQ connection factory. host=[%s], port=[%s], username=[%s]\n",
                rabbitPropertiesConfig.getHost(), rabbitPropertiesConfig.getPort(),
                rabbitPropertiesConfig.getUsername());
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitPropertiesConfig.getHost());
        connectionFactory.setPort(rabbitPropertiesConfig.getPort());
        connectionFactory.setUsername(rabbitPropertiesConfig.getUsername());
        connectionFactory.setPassword(rabbitPropertiesConfig.getPassword());
        connectionFactory.setVirtualHost(rabbitPropertiesConfig.getVirtualHost());
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