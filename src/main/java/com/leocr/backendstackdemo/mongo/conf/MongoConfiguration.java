package com.leocr.backendstackdemo.mongo.conf;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    public static final String MONGO_REPO_BASE_PACKAGE = "com.leocr.backendstackdemo.mongo.repo";

    @Getter
    @Setter
    @Value("${mongo.database.name}")
    public String databaseName;

    @Getter
    @Setter
    @Value("${mongo.connection.string}")
    private String connectionString;

    @Override
    public @NotNull MongoClient mongoClient() {
        final ConnectionString conn = new ConnectionString(this.connectionString);
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(conn)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public @NotNull Collection<String> getMappingBasePackages() {
        return Collections.singleton(MONGO_REPO_BASE_PACKAGE);
    }
}
