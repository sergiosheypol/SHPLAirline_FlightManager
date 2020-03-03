package com.shpl.flightbooking.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "SHPL_BOOKINGS")
public class BookingEntity {

    @Id
    private BookingKey key;

    @DynamoDBAttribute
    private String price;

    @DynamoDBAttribute
    private LocalDateTime dateOfBooking;

}
