package com.leocr.backendstackdemo.kafka.service.impl;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import com.leocr.backendstackdemo.redis.model.Message;
import com.leocr.backendstackdemo.kafka.service.KafkaService;
import com.leocr.backendstackdemo.redis.repo.RedisMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
public class KafkaServiceImpl implements KafkaService {

    private static final String KAFKA_RECEIVED_MESSAGE = "[Kafka][{}][{}] Received Message: {}";
    private static final String KAFKA_MESSAGE_SAVED_ON_REDIS = "[Kafka] Message saved on Redis: {}";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ConfigurationProperties configurationProperties;

    private final RedisMessageRepository repository;

    @Autowired
    public KafkaServiceImpl(KafkaTemplate<String, String> kafkaTemplate,
                            ConfigurationProperties configurationProperties, RedisMessageRepository repository) {
        this.kafkaTemplate = kafkaTemplate;
        this.configurationProperties = configurationProperties;
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String produce(@NotNull Integer value) {
        final String topicName = configurationProperties.getKafkaTopicName();
        final String valueStr = String.valueOf(value);
        kafkaTemplate.send(topicName, valueStr);
        return valueStr;
    }

    /**
     * {@inheritDoc}
     */
    @KafkaListener(topics = "#{configurationProperties.getKafkaTopicName()}",
            groupId = "#{configurationProperties.getKafkaTopicGroupId()}")
    @Override
    public void consume(@NotNull String message) {
        log.info(KAFKA_RECEIVED_MESSAGE, configurationProperties.getKafkaTopicGroupId(), configurationProperties.getKafkaTopicName(), message);
        final Integer value = Integer.valueOf(message);
        final Message msg = new Message(value);
        repository.save(msg);
        log.info(KAFKA_MESSAGE_SAVED_ON_REDIS, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> list() {
        final Iterable<Message> messages = repository.findAll();
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(messages.iterator(), Spliterator.NONNULL), false)
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getValue)
                .map(Object::toString)
                .collect(toSet());
    }
}
