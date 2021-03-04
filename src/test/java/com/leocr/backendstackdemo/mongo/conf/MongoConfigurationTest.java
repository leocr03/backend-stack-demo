package com.leocr.backendstackdemo.mongo.conf;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MongoConfigurationTest {

    private MongoConfiguration configuration;

    @BeforeEach
    void setUp() {
        configuration = new MongoConfiguration();
        configuration.setDatabaseName("test");
        configuration.setConnectionString("mongodb://mongo:27017/test");
    }

    @Test
    void mongoClient() {
        final MongoClient mongoClient = configuration.mongoClient();
        assertNotNull(mongoClient);
    }

    @Test
    void getMappingBasePackages() {
        final Collection<String> mappingBasePackages = configuration.getMappingBasePackages();
        assertNotNull(mappingBasePackages);
    }
}