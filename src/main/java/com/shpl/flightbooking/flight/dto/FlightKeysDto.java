package com.shpl.flightbooking.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightKeysDto {
    @NotBlank(message = "Flight ID is mandatory")
    private String id;

    @NotBlank(message = "Iata Code is mandatory")
    private String iataCode;
}
