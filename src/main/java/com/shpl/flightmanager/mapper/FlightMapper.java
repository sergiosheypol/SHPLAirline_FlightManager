package com.shpl.flightmanager.mapper;

import com.shpl.flightmanager.dto.FlightDto;
import com.shpl.flightmanager.dto.FlightInfoResponseDto;
import com.shpl.flightmanager.dto.FlightPushDto;
import com.shpl.flightmanager.dto.FlightRemainingSeatsDto;
import com.shpl.flightmanager.entity.Flight;
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

    public Flight flightDtoToFlight(FlightDto flightDto) {
        return Flight.builder()
                .iataCode(flightDto.getIataCode())
                .id(flightDto.getId())
                .soldSeats(flightDto.getSoldSeats())
                .totalSeatsAvailable(flightDto.getTotalSeatsAvailable())
                .connectingAirport(flightDto.getConnectingAirport())
                .departureAirport(flightDto.getDepartureAirport())
                .arrivalAirport(flightDto.getArrivalAirport())
                .arrivalDate(flightDto.getArrivalDate().toString())
                .departureDate(flightDto.getDepartureDate().toString())
                .passengersPnr(flightDto.getPassengersPnr())
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
                .build();
    }

    public FlightRemainingSeatsDto flightToFlightRemainingSeats(Flight flight) {
        return FlightRemainingSeatsDto.builder()
                .soldSeats(flight.getSoldSeats())
                .totalSeatsAvailable(flight.getTotalSeatsAvailable())
                .admitsNewBookings(flight.getSoldSeats() < flight.getTotalSeatsAvailable())
                .build();
    }
}
