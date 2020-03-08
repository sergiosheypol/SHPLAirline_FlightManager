package com.shpl.flightbooking.flight_crud.mapper;

import com.shpl.flightbooking.flight_crud.dto.FlightInfoResponseDto;
import com.shpl.flightbooking.flight_crud.dto.FlightPushDto;
import com.shpl.flightbooking.flight_crud.entity.Flight;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FlightMapper {

    @Value("${aircraft.passengers}")
    private String nPassengers;

    public Flight flightPushDtoToFlight(FlightPushDto flightPushDto) {
        return Flight.builder()
                .iataCode(flightPushDto.getIataCode())
                .id(flightPushDto.getId())
                .soldSeats(0)
                .totalSeatsAvailable(Integer.parseInt(nPassengers))
                .connectingAirport(flightPushDto.getConnectingAirport())
                .departureAirport(flightPushDto.getDepartureAirport())
                .arrivalAirport(flightPushDto.getArrivalAirport())
                .arrivalDate(flightPushDto.getArrivalDate().toString())
                .departureDate(flightPushDto.getDepartureDate().toString())
                .build();
    }

    public Flight flightPushDtoToFlight(FlightPushDto flightPushDto, int soldSeats) {
        return Flight.builder()
                .iataCode(flightPushDto.getIataCode())
                .id(flightPushDto.getId())
                .soldSeats(soldSeats)
                .totalSeatsAvailable(Integer.parseInt(nPassengers))
                .connectingAirport(flightPushDto.getConnectingAirport())
                .departureAirport(flightPushDto.getDepartureAirport())
                .arrivalAirport(flightPushDto.getArrivalAirport())
                .arrivalDate(flightPushDto.getArrivalDate().toString())
                .departureDate(flightPushDto.getDepartureDate().toString())
                .build();
    }

    public FlightInfoResponseDto flightToFlightInfoResponseDto(Flight flight) {
        return FlightInfoResponseDto.builder()
                .arrivalAirport(flight.getArrivalAirport())
                .arrivalDate(LocalDateTime.parse(flight.getArrivalDate()))
                .connectingAirport(flight.getConnectingAirport())
                .departureAirport(flight.getDepartureAirport())
                .departureDate(LocalDateTime.parse(flight.getDepartureDate()))
                .iataCode(flight.getIataCode())
                .id(flight.getId())
                .soldSeats(flight.getSoldSeats())
                .totalSeatsAvailable(flight.getTotalSeatsAvailable())
                .build();
    }
}
