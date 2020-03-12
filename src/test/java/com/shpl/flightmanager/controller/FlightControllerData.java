package com.shpl.flightmanager.controller;

import com.shpl.flightmanager.dto.FlightDto;
import com.shpl.flightmanager.dto.FlightPushDto;
import com.shpl.flightmanager.service.PnrService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                    .id("FRAAVVBB")
                    .iataCode("FR5676")
                    .departureAirport("MAD")
                    .arrivalAirport("IBZ")
                    .connectingAirport("null")
                    .departureDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .arrivalDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .passengersPnr(generateTestPnrList(198))
                    .soldSeats(198)
                    .totalSeatsAvailable(198)
                    .build();

    public static final FlightDto testFlight158Sold =
            FlightDto.builder()
                    .id("ERWWETRETERTERT")
                    .iataCode("FR5996")
                    .departureAirport("MAD")
                    .arrivalAirport("IBZ")
                    .connectingAirport("null")
                    .departureDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .arrivalDate(LocalDateTime.parse("2020-02-27T07:10:00"))
                    .passengersPnr(generateTestPnrList(158))
                    .soldSeats(158)
                    .totalSeatsAvailable(198)
                    .build();

    public static List<String> generateTestPnrList(int size) {
        List<String> list = new ArrayList<>();
        PnrService pnrService = new PnrService();

        for (int i = 0; i < size; i++) {
            list.add(pnrService.generatePnr());
        }

        return list;
    }
}
