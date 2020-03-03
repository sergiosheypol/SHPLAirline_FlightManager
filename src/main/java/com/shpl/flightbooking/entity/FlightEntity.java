package com.shpl.flightbooking.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "SHPL_FLIGHTS")
public class FlightEntity {

    @Id
    private FlightKey key;

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

    @DynamoDBAttribute
    private int totalSeatsAvailable;

    @DynamoDBAttribute
    private int soldSeats;

    @Data
    @DynamoDBDocument
    public static class Passengers{
        private List<String> passengersPnr;
    }


}
