package com.shpl.flightbooking.booking.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "SHPL_BOOKINGS")
public class Booking {

    @DynamoDBHashKey
    private String pnr;

    @DynamoDBRangeKey
    private String flightId;

    @DynamoDBAttribute
    private String price;

    @DynamoDBAttribute
    private LocalDateTime dateOfBooking;
}
