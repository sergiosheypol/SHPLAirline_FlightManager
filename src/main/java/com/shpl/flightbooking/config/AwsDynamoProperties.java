package com.shpl.flightbooking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dynamodb.flightstable")
@EnableConfigurationProperties
public class AwsDynamoProperties {
    private String tableName;
}
