package com.leocr.backendstackdemo.persistence.redis.config;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories(basePackages = "com.leocr.backendstackdemo.persistence.redis.repo")
@Data
@Configuration
public class RedisConfiguration {

    private final ConfigurationProperties config;

    @Autowired
    public RedisConfiguration(ConfigurationProperties config) {
        this.config = config;
    }

    @Bean
    @NotNull
    JedisConnectionFactory jedisConnectionFactory() {
        final RedisStandaloneConfiguration clusterConfig = new RedisStandaloneConfiguration(config.getRedisHostName(),
                config.getRedisPort());
        return new JedisConnectionFactory(clusterConfig);
    }

    @Bean
    public @NotNull RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
