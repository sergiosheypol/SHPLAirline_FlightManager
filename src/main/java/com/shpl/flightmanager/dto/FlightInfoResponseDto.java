package com.shpl.flightmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightInfoResponseDto {
    private String id;
    private String iataCode;
    private String departureAirport;
    private String arrivalAirport;
    private String connectingAirport;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;

}
