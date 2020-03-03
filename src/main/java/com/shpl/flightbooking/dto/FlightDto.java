package com.shpl.flightbooking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightDto {
    private String id;
    private String iataCode;
    private String departureAirport;
    private String arrivalAirport;
    private String connectingAirport;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private int totalSeatsAvailable;
    private int soldSeats;
}
