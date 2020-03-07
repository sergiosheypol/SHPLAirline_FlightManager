package com.shpl.flightbooking.ITtests;

import com.shpl.flightbooking.dto.FlightPushDto;

import java.time.LocalDateTime;

public final class FlightControllerData {

    public static final FlightPushDto testFlight =
            FlightPushDto.builder()
                    .id("FRXXXXXXXXXX")
                    .iataCode("FRXXXX")
                    .departureAirport("MAD")
                    .arrivalAirport("IBZ")
                    .connectingAirport("null")
                    .departureDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .arrivalDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .build();
}
