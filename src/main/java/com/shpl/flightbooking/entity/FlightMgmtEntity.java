package com.shpl.flightbooking.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@DynamoDBTable(tableName = "FLIGHT-MGMT")
public class FlightMgmtEntity {

    @DynamoDBHashKey
    private String id;

    @DynamoDBRangeKey
    private String type;

    @DynamoDBAttribute
    private String flightNumber;

    @DynamoDBAttribute
    private String departureAirport;

    @DynamoDBAttribute
    private String arrivalAirport;

    @DynamoDBAttribute
    private String connectingAirport;

    @DynamoDBAttribute
    private LocalDateTime departureDate;

    @DynamoDBAttribute
    private LocalDateTime arrivalDate;
}
