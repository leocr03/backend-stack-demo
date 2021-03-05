package com.leocr.backendstackdemo.mongo.conf;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MongoConfigurationTest {

    private MongoConfiguration configuration;

    @Mock
    private ConfigurationProperties configProperties;

    @BeforeEach
    void setUp() {
        configuration = new MongoConfiguration(configProperties);
    }

    @Test
    void mongoClient() {
        when(configProperties.getMongoConnectionString()).thenReturn("mongodb://mongo:27017/test");
        final MongoClient mongoClient = configuration.mongoClient();
        assertNotNull(mongoClient);
    }

    @Test
    void getMappingBasePackages() {
        final Collection<String> mappingBasePackages = configuration.getMappingBasePackages();
        assertNotNull(mappingBasePackages);
    }
}