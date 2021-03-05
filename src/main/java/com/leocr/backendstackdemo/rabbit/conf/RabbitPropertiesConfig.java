package com.leocr.backendstackdemo.rabbit.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitPropertiesConfig {

    @Getter
    @Value(value = "${rabbit.host}")
    private String host;

    @Getter
    @Value(value = "${rabbit.port}")
    private Integer port;

    @Getter
    @Value(value = "${rabbit.username}")
    private String username;

    @Getter
    @Value(value = "${rabbit.password}")
    private String password;

    @Getter
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
}
