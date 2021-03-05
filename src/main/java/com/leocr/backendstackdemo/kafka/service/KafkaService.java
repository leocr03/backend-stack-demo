package com.leocr.backendstackdemo.kafka.service;

import com.leocr.backendstackdemo.common.model.Message;
import com.leocr.backendstackdemo.kafka.conf.KafkaTopicConfig;
import com.leocr.backendstackdemo.redis.repo.RedisMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Comparator;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
public class KafkaService {

    private static final String KAFKA_RECEIVED_MESSAGE = "[Kafka][{}][{}] Received Message: {}";
    private static final String KAFKA_MESSAGE_SAVED_ON_REDIS = "[Kafka] Message saved on Redis: {}";
    private static final String ARGUMENT_VALUE_CANNOT_BE_NULL = "Argument \"value\" cannot be null.";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaTopicConfig kafkaTopicConfig;

    protected final RedisMessageRepository redisMessageRepository;

    @Autowired
    public KafkaService(KafkaTemplate<String, String> kafkaTemplate, KafkaTopicConfig kafkaTopicConfig,
                        RedisMessageRepository redisMessageRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicConfig = kafkaTopicConfig;
        this.redisMessageRepository = redisMessageRepository;
    }

    public String produce(Integer value) {
        if (value != null) {
            final String topicName = kafkaTopicConfig.getTopicName();
            kafkaTemplate.send(topicName, String.valueOf(value));
            return String.valueOf(value);
        } else {
            throw new IllegalArgumentException(ARGUMENT_VALUE_CANNOT_BE_NULL);
        }
    }

    @KafkaListener(topics = "#{kafkaTopicConfig.getTopicName()}", groupId = "#{kafkaTopicConfig.getGroupId()}")
    public String consume(String message) {
        log.info(KAFKA_RECEIVED_MESSAGE,  kafkaTopicConfig.getGroupId(),
                kafkaTopicConfig.getTopicName(), message);
        final Integer value = Integer.valueOf(message);
        final Message msg = new Message(value);
        redisMessageRepository.save(msg);
        log.info(KAFKA_MESSAGE_SAVED_ON_REDIS, message);
        return message;
    }

    public Set<String> list() {
        final Iterable<Message> messages = redisMessageRepository.findAll();
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(messages.iterator(), Spliterator.NONNULL), false)
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getValue)
                .map(Object::toString)
                .collect(toSet());
    }
}
