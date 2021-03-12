package com.leocr.backendstackdemo.broker.rabbit.conf;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import com.leocr.backendstackdemo.common.service.BrokerService;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

    private final ConfigurationProperties configurationProperties;

    @Autowired
    public RabbitConfig(ConfigurationProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
    }

    @Bean
    @NotNull
    Queue queue() {
        return new Queue(configurationProperties.getRabbitQueue(), false, false, false);
    }

    @Bean
    @NotNull
    TopicExchange exchange() {
        return new TopicExchange(configurationProperties.getRabbitExchange());
    }

    @Bean
    @NotNull
    Binding binding(@NotNull Queue queue, @NotNull TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(configurationProperties.getRabbitRoutingKey());
    }

    @Bean
    @NotNull
    SimpleMessageListenerContainer container(@NotNull ConnectionFactory connectionFactory,
                                             @NotNull MessageListenerAdapter listenerAdapter) {
        final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(configurationProperties.getRabbitQueue());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public @NotNull ConnectionFactory connectionFactory() {
        System.out.printf("Starting RabbitMQ connection factory. host=[%s], port=[%s], username=[%s]\n",
                configurationProperties.getRabbitHost(), configurationProperties.getRabbitPort(),
                configurationProperties.getRabbitUsername());
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(configurationProperties.getRabbitHost());
        connectionFactory.setPort(configurationProperties.getRabbitPort());
        connectionFactory.setUsername(configurationProperties.getRabbitUsername());
        connectionFactory.setPassword(configurationProperties.getRabbitPassword());
        connectionFactory.setVirtualHost(configurationProperties.getRabbitVirtualHost());
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    @Bean
    @NotNull
    MessageListenerAdapter listenerAdapter(@NotNull @Qualifier("RabbitService") BrokerService brokerService) {
        return new MessageListenerAdapter(brokerService, "consume");
    }

    @Bean
    public @NotNull RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
}