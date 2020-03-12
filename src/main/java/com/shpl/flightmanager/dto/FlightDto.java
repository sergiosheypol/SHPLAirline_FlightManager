package com.shpl.flightmanager.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private List<String> passengersPnr;
}
