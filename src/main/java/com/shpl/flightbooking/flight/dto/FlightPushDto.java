package com.shpl.flightbooking.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightPushDto {

    @NotBlank(message = "Wrong id")
    private String id;

    @NotBlank(message = "Wrong iataCode")
    private String iataCode;

    @NotBlank(message = "Wrong departureAirport")
    private String departureAirport;

    @NotBlank(message = "Wrong arrivalAirport")
    private String arrivalAirport;

    private String connectingAirport;

    @NotNull(message = "Wrong departureDate")
    private LocalDateTime departureDate;

    @NotNull(message = "Wrong arrivalDate")
    private LocalDateTime arrivalDate;
}
