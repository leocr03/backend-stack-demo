package com.leocr.backendstackdemo.kafka.service;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import com.leocr.backendstackdemo.common.model.Message;
import com.leocr.backendstackdemo.redis.repo.RedisMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@Transactional
public class KafkaService {

    private static final String KAFKA_RECEIVED_MESSAGE = "[Kafka][{}][{}] Received Message: {}";
    private static final String KAFKA_MESSAGE_SAVED_ON_REDIS = "[Kafka] Message saved on Redis: {}";
    private static final String ARGUMENT_VALUE_CANNOT_BE_NULL = "Argument \"value\" cannot be null.";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ConfigurationProperties configurationProperties;

    private final RedisMessageRepository repository;

    @Autowired
    public KafkaService(KafkaTemplate<String, String> kafkaTemplate, ConfigurationProperties configurationProperties,
                        RedisMessageRepository repository) {
        this.kafkaTemplate = kafkaTemplate;
        this.configurationProperties = configurationProperties;
        this.repository = repository;
    }

    public String produce(@Nullable Integer value) {
        if (value != null) {
            final String topicName = configurationProperties.getKafkaTopicName();
            final String valueStr = String.valueOf(value);
            kafkaTemplate.send(topicName, valueStr);
            return valueStr;
        } else {
            throw new IllegalArgumentException(ARGUMENT_VALUE_CANNOT_BE_NULL);
        }
    }

    @KafkaListener(topics = "#{configurationProperties.getKafkaTopicName()}",
            groupId = "#{configurationProperties.getKafkaTopicGroupId()}")
    public @NotNull String consume(@NotNull String message) {
        log.info(KAFKA_RECEIVED_MESSAGE, configurationProperties.getKafkaTopicGroupId(), configurationProperties.getKafkaTopicName(), message);
        final Integer value = Integer.valueOf(message);
        final Message msg = new Message(value);
        repository.save(msg);
        log.info(KAFKA_MESSAGE_SAVED_ON_REDIS, message);
        return message;
    }

    public Set<String> list() {
        final Iterable<Message> messages = repository.findAll();
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(messages.iterator(), Spliterator.NONNULL), false)
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getValue)
                .map(Object::toString)
                .collect(toSet());
    }
}
