package com.leocr.backendstackdemo.mongo.conf;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Override
    public @NotNull MongoClient mongoClient() {
        final ConnectionString connectionString = new ConnectionString("mongodb://mongo:27017/test");
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected @NotNull String getDatabaseName() {
        return "test";
    }

    @Override
    public @NotNull Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.leocr.backendstackdemo.mongo.repo");
    }
}
