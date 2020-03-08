package com.shpl.flightbooking.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.localstack.LocalStackContainer;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;

@TestConfiguration
public class AwsDynamoDBTestConfig {

    @Bean
    public LocalStackContainer localStack() {
        LocalStackContainer localStackContainer = new LocalStackContainer().withServices(DYNAMODB);
        localStackContainer.start();
        return localStackContainer;
    }

    @Bean
    @Primary
    @DependsOn("localStack")
    public AmazonDynamoDB dynamoDB(final LocalStackContainer lSContainer) {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(lSContainer.getDefaultCredentialsProvider())
                .withEndpointConfiguration(lSContainer.getEndpointConfiguration(DYNAMODB))
                .build();
    }
}
