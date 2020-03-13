package com.shpl.flightmanager.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "mongodb")
public class MongoDBProperties {
    private String endpoint;
    private String databaseName;
    private String flightsCollectionName;
    private String username;
    private String password;
    private int port;
}
