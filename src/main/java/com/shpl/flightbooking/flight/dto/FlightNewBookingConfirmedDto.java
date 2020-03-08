package com.shpl.flightbooking.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightNewBookingConfirmedDto {
    private String pnr;
    private FlightInfoResponseDto flightInfoResponseDto;
}
