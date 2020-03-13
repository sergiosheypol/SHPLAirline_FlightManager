package com.shpl.flightmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Flight {

    private String id;

    private String iataCode;

    private String departureAirport;

    private String arrivalAirport;

    private String connectingAirport;

    private String departureDate;

    private String arrivalDate;

    private int totalSeatsAvailable;

    private int soldSeats;

    private List<String> passengersPnr;
}
