package com.shpl.flightbooking.service;

import com.shpl.flightbooking.dto.FlightInfoDto;
import com.shpl.flightbooking.dto.FlightKeysDto;
import com.shpl.flightbooking.dto.FlightPushDto;
import com.shpl.flightbooking.model.Flight;
import com.shpl.flightbooking.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public void saveFlight(FlightPushDto flightPushDto) {
        flightRepository.save(flightPushDto);
    }

    public FlightInfoDto findFlight(FlightKeysDto flightKeysDto) {
        return mapFlightDto(flightRepository.findFlight(flightKeysDto));
    }
    
    private FlightInfoDto mapFlightDto(Flight flight) {
        return FlightInfoDto.builder()
                .arrivalAirport(flight.getArrivalAirport())
                .arrivalDate(flight.getArrivalDate())
                .connectingAirport(flight.getConnectingAirport())
                .departureAirport(flight.getDepartureAirport())
                .departureDate(flight.getDepartureDate())
                .iataCode(flight.getIataCode())
                .id(flight.getId())
                .soldSeats(flight.getSoldSeats())
                .totalSeatsAvailable(flight.getTotalSeatsAvailable())
                .build();
    }





}
