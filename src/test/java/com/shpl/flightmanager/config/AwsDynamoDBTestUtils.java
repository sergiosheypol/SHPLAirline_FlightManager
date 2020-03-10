package com.shpl.flightmanager.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.shpl.flightmanager.ITtests.FlightControllerData;
import com.shpl.flightmanager.mapper.FlightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AwsDynamoDBTestUtils {

    public final static String SHPL_FLIGHTS_NAME = "SHPL_FLIGHTS";

    @Autowired
    private DynamoDBMapper mapper;

    @Autowired
    private FlightMapper flightMapper;

    public void loadTables(AmazonDynamoDB dynamoDB) {
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

    public void resetTables(AmazonDynamoDB dynamoDB) {
        dynamoDB.deleteTable(SHPL_FLIGHTS_NAME);
    }

    public void loadTestFlight(AmazonDynamoDB dynamoDB) {
        mapper.save(flightMapper.flightPushDtoToFlight(FlightControllerData.testFlight));
    }

    public void loadFullFlight(AmazonDynamoDB dynamoDB) {
        mapper.save(flightMapper.flightDtoToFlight(FlightControllerData.fullFlight));
    }
}
