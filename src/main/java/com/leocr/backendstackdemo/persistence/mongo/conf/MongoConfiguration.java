package com.leocr.backendstackdemo.persistence.mongo.conf;

import com.leocr.backendstackdemo.common.conf.ConfigurationProperties;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@EnableMongoRepositories(basePackages = "com.leocr.backendstackdemo.persistence.mongo.repo")
@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    public static final String MONGO_REPO_BASE_PACKAGE = "com.leocr.backendstackdemo.persistence.mongo.repo";

    private final ConfigurationProperties config;

    @Autowired
    public MongoConfiguration(ConfigurationProperties config) {
        this.config = config;
    }

    @Override
    public @NotNull MongoClient mongoClient() {
        final ConnectionString conn = new ConnectionString(config.getMongoConnectionString());
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(conn)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected @NotNull String getDatabaseName() {
        return config.getMongoDatabaseName();
    }

    @Override
    public @NotNull Collection<String> getMappingBasePackages() {
        return Collections.singleton(MONGO_REPO_BASE_PACKAGE);
    }
}
