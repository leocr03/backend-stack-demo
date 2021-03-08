package com.leocr.backendstackdemo.rabbit.service.impl;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import com.leocr.backendstackdemo.common.model.Message;
import com.leocr.backendstackdemo.mongo.repo.MongoMessageRepository;
import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@Transactional
public class RabbitServiceImpl implements RabbitService {

    private static final String ARGUMENT_VALUE_CANNOT_BE_NULL = "Argument \"value\" cannot be null.";
    private static final String RABBIT_MQ_RECEIVED_MESSAGE = "[RabbitMQ][{}][{}] Received Message: {}}\n";
    private static final String RABBIT_MQ_MESSAGE_SAVED_ON_REDIS = "[RabbitMQ] Message saved on Redis: {}";

    private final RabbitTemplate rabbitTemplate;

    private final ConfigurationProperties configurationProperties;

    private final MongoMessageRepository repository;

    @Autowired
    public RabbitServiceImpl(RabbitTemplate rabbitTemplate, ConfigurationProperties configurationProperties,
                         MongoMessageRepository repository) {
        this.rabbitTemplate = rabbitTemplate;
        this.configurationProperties = configurationProperties;
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String produce(@Nullable Integer value) {
        if (value != null) {
            final String message = value.toString();
            final String routingKey = configurationProperties.getRabbitRoutingKey();
            final String exchange = configurationProperties.getRabbitExchange();
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            return String.valueOf(value);
        } else {
            throw new IllegalArgumentException(ARGUMENT_VALUE_CANNOT_BE_NULL);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RabbitListener(queues = "#{configurationProperties.getRabbitQueue()}")
    public @NotNull String consume(@NotNull String message) {
        log.info(RABBIT_MQ_RECEIVED_MESSAGE, configurationProperties.getRabbitExchange(),
                configurationProperties.getRabbitRoutingKey(), message);
        final Integer value = Integer.valueOf(message);
        final Message msg = new Message(value);
        repository.save(msg);
        log.info(RABBIT_MQ_MESSAGE_SAVED_ON_REDIS, message);
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> list() {
        final List<Message> messages = repository.findAll();
        return messages.stream()
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getValue)
                .map(Object::toString)
                .collect(toSet());
    }
}
