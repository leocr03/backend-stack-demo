package com.leocr.backendstackdemo.redis.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Data
@Configuration
public class RedisConfiguration {

    @Getter
    @Value(value = "${redis.hostName:redis}")
    private String hostName;

    @Getter
    @Value(value = "${redis.port:6379}")
    private int port;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        final RedisStandaloneConfiguration clusterConfig = new RedisStandaloneConfiguration(hostName, port);
        return new JedisConnectionFactory(clusterConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
