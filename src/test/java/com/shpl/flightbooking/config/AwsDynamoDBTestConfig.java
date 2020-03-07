package com.shpl.flightbooking.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.shpl.flightbooking.ITtests.FlightControllerData;
import com.shpl.flightbooking.dto.FlightPushDto;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.localstack.LocalStackContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;

@TestConfiguration
public class AwsDynamoDBTestConfig {

    public final static String SHPL_FLIGHTS_NAME = "SHPL_FLIGHTS";

    public static void loadTables(AmazonDynamoDB dynamoDB) {
        String partitionKey = "id";
        String sortKey = "iataCode";

        // Common attributes for tables
        List<AttributeDefinition> attributes = new ArrayList<>();
        attributes.add(new AttributeDefinition(partitionKey, ScalarAttributeType.S));
        attributes.add(new AttributeDefinition(sortKey, ScalarAttributeType.S));

        List<KeySchemaElement> keys = new ArrayList<>();
        keys.add(new KeySchemaElement(partitionKey, KeyType.HASH)); // HASH == partition Key
        keys.add(new KeySchemaElement(sortKey, KeyType.RANGE)); // RANGE == sort key

        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(10L, 10L);

        dynamoDB.createTable(attributes, SHPL_FLIGHTS_NAME, keys, provisionedThroughput);

    }

    public static void resetTables(AmazonDynamoDB dynamoDB) {
        dynamoDB.deleteTable(SHPL_FLIGHTS_NAME);
    }

    public static void loadTestFlight(AmazonDynamoDB dynamoDB) {
        dynamoDB.putItem(SHPL_FLIGHTS_NAME, generateAttributesMap(FlightControllerData.testFlight));
    }

    private static Map<String, AttributeValue> generateAttributesMap(FlightPushDto flightPushDto) {
        HashMap<String, AttributeValue> map = new HashMap<>();
        map.put("id", new AttributeValue(flightPushDto.getId()));
        map.put("iataCode", new AttributeValue(flightPushDto.getIataCode()));
        map.put("departureAirport", new AttributeValue(flightPushDto.getDepartureAirport()));
        map.put("arrivalAirport", new AttributeValue(flightPushDto.getArrivalAirport()));
        map.put("connectingAirport", new AttributeValue(flightPushDto.getConnectingAirport()));
        map.put("departureDate", new AttributeValue(flightPushDto.getDepartureDate().toString()));
        map.put("arrivalDate", new AttributeValue(flightPushDto.getArrivalDate().toString()));
        map.put("totalSeatsAvailable", new AttributeValue().withN("198"));
        map.put("soldSeats", new AttributeValue().withN("0"));
        map.put("passengersPnr", new AttributeValue().withSS("ER45TY"));
        return map;
    }

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
