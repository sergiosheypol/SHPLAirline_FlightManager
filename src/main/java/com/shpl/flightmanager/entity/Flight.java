package com.shpl.flightmanager.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

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

    private Passengers passengers;

    @DynamoDBDocument
    @Getter
    @Setter
    @Builder
    public static class Passengers {

        @DynamoDBAttribute(attributeName = "passengersPnr")
        private List<String> passengersPnr;
    }

}
