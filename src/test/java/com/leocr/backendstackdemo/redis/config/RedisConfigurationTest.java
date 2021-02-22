package com.leocr.backendstackdemo.redis.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

class RedisConfigurationTest {

    private RedisConfiguration config;

    @BeforeEach
    void setUp() {
        config = new RedisConfiguration();
    }

    @Test
    void jedisConnectionFactory() {
        config.setHostName("someHostName");
        config.setPort(1000);
        final JedisConnectionFactory jedisConnectionFactory = config.jedisConnectionFactory();
        assertNotNull(jedisConnectionFactory);
    }

    @Test
    void redisTemplate() {
        config.setHostName("someHostName");
        config.setPort(1000);
        final RedisTemplate<String, Object> template = config.redisTemplate();
        assertNotNull(template);
    }
}