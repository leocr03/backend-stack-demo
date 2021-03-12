package com.leocr.backendstackdemo.persistence.redis.config;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisConfigurationTest {

    private RedisConfiguration config;

    @Mock
    private ConfigurationProperties configProperties;

    @BeforeEach
    void setUp() {
        config = new RedisConfiguration(configProperties);
    }

    @Test
    void jedisConnectionFactory() {
        when(configProperties.getRedisHostName()).thenReturn("someHostName");
        when(configProperties.getRedisPort()).thenReturn(1000);
        final JedisConnectionFactory jedisConnectionFactory = config.jedisConnectionFactory();
        assertNotNull(jedisConnectionFactory);
    }

    @Test
    void redisTemplate() {
        when(configProperties.getRedisHostName()).thenReturn("someHostName");
        when(configProperties.getRedisPort()).thenReturn(1000);
        final RedisTemplate<String, Object> template = config.redisTemplate();
        assertNotNull(template);
    }
}