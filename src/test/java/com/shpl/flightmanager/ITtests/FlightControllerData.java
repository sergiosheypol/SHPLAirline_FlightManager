package com.shpl.flightmanager.ITtests;

import com.shpl.flightmanager.dto.FlightDto;
import com.shpl.flightmanager.dto.FlightPushDto;

import java.time.LocalDateTime;

public final class FlightControllerData {

    public static final FlightPushDto testFlight =
            FlightPushDto.builder()
                    .id("FRAAAAAAA")
                    .iataCode("FRAAAA")
                    .departureAirport("MAD")
                    .arrivalAirport("IBZ")
                    .connectingAirport("null")
                    .departureDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .arrivalDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .build();

    public static final FlightPushDto testFlight2 =
            FlightPushDto.builder()
                    .id("FREEEEEEEEE")
                    .iataCode("FREAEA")
                    .departureAirport("MAD")
                    .arrivalAirport("IBZ")
                    .connectingAirport("null")
                    .departureDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .arrivalDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .build();

    public static final FlightDto fullFlight =
            FlightDto.builder()
                    .id("FR-AA-VV-BB")
                    .iataCode("FR5676")
                    .departureAirport("MAD")
                    .arrivalAirport("IBZ")
                    .connectingAirport("null")
                    .departureDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .arrivalDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .soldSeats(198)
                    .totalSeatsAvailable(198)
                    .build();


    public static final FlightPushDto wrongKey =
            FlightPushDto.builder().build();
}
