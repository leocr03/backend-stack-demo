package com.leocr.backendstackdemo.kafka.service;

import com.leocr.backendstackdemo.common.model.Message;
import com.leocr.backendstackdemo.kafka.conf.KafkaTopicConfig;
import com.leocr.backendstackdemo.redis.repo.RedisMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
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
        logger.info(KAFKA_RECEIVED_MESSAGE,  kafkaTopicConfig.getGroupId(),
                kafkaTopicConfig.getTopicName(), message);
        final Integer value = Integer.valueOf(message);
        final Message msg = new Message(value);
        redisMessageRepository.save(msg);
        logger.info(KAFKA_MESSAGE_SAVED_ON_REDIS, message);
        return message;
    }

    public List<String> list() {
        final Iterable<Message> messages = redisMessageRepository.findAll();
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(messages.iterator(), Spliterator.NONNULL), false)
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getValue)
                .map(Object::toString)
                .collect(toList());
    }
}
