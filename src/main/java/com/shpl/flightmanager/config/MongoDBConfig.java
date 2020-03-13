package com.shpl.flightmanager.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MongoDBProperties.class)
public class MongoDBConfig extends AbstractReactiveMongoConfiguration {

    private final MongoDBProperties mongoDBProperties;

    @Override
    @Bean
    @Primary
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(getFullUrl());
    }

    private String getFullUrl() {
        return String.format("mongodb://%s:%s@%s:%d",
                mongoDBProperties.getUsername(),
                mongoDBProperties.getPassword(),
                mongoDBProperties.getEndpoint(),
                mongoDBProperties.getPort());
    }

    @Override
    protected String getDatabaseName() {
        return mongoDBProperties.getDatabaseName();
    }
}
