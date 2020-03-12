package com.shpl.flightmanager.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

import java.util.List;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "SHPL_FLIGHTS")
public class Flight {

    @DynamoDBHashKey
    private String id;

    @DynamoDBRangeKey
    private String iataCode;

    @DynamoDBAttribute
    private String departureAirport;

    @DynamoDBAttribute
    private String arrivalAirport;

    @DynamoDBAttribute
    private String connectingAirport;

    @DynamoDBAttribute
    private String departureDate;

    @DynamoDBAttribute
    private String arrivalDate;

    @DynamoDBAttribute
    private int totalSeatsAvailable;

    @DynamoDBAttribute
    private int soldSeats;

    @DynamoDBAttribute
    private List<String> passengersPnr;
}
