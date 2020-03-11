package com.shpl.flightmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingResult {
    private FlightBookingStatus flightBookingStatus;
    private String pnr;
}
