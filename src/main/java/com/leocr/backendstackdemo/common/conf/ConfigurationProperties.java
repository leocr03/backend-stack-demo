package com.leocr.backendstackdemo.common.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationProperties {

    @Getter
    @Value(value = "${kafka.topic.first.bootstrap.address}")
    private String kafkaTopicBootstrapAddress;

    @Getter
    @Value(value = "${kafka.topic.first.topic.name}")
    private String kafkaTopicName;

    @Getter
    @Value(value = "${kafka.topic.first.group.id}")
    private String kafkaTopicGroupId;

    @Getter
    @Value(value = "${kafka.topic.first.num.partitions}")
    private Integer kafkaTopicNumPartitions;

    @Getter
    @Value(value = "${kafka.topic.first.replication.factor}")
    private Integer kafkaTopicReplicationFactor;

    @Getter
    @Value(value = "${rabbit.host}")
    private String rabbitHost;

    @Getter
    @Value(value = "${rabbit.port}")
    private Integer rabbitPort;

    @Getter
    @Value(value = "${rabbit.username}")
    private String rabbitUsername;

    @Getter
    @Value(value = "${rabbit.password}")
    private String rabbitPassword;

    @Getter
    @Value(value = "${rabbit.queue}")
    private String rabbitQueue;

    @Getter
    @Value(value = "${rabbit.exchange}")
    private String rabbitExchange;

    @Getter
    @Value(value = "${rabbit.topic.first.routing.key}")
    private String rabbitRoutingKey;

    @Getter
    @Value(value = "${rabbit.virtual.host}")
    private String rabbitVirtualHost;

    @Getter
    @Value(value = "${redis.host.name}")
    private String redisHostName;

    @Getter
    @Value(value = "${redis.port}")
    private int redisPort;

    @Getter
    @Value("${mongo.database.name}")
    public String mongoDatabaseName;

    @Getter
    @Value("${mongo.connection.string}")
    private String mongoConnectionString;
}
