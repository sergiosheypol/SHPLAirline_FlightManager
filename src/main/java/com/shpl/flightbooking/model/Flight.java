package com.shpl.flightbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    private String id;

    private String iataCode;

    private String departureAirport;

    private String arrivalAirport;

    private String connectingAirport;

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

    private int totalSeatsAvailable;

    private int soldSeats;

    private List<String> passengersPnr;

}
