package com.shpl.flightbooking.service;

import com.shpl.flightbooking.dto.FlightInfoResponseDto;
import com.shpl.flightbooking.dto.FlightKeysDto;
import com.shpl.flightbooking.dto.FlightPushDto;
import com.shpl.flightbooking.model.Flight;
import com.shpl.flightbooking.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public void saveFlight(FlightPushDto flightPushDto) {
        flightRepository.save(flightPushDto);
    }

    public Mono<FlightInfoResponseDto> findFlight(FlightKeysDto flightKeysDto) {
        return flightRepository.findFlight(flightKeysDto)
                .map(this::mapFlightDto)
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }

    private FlightInfoResponseDto mapFlightDto(Flight flight) {
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
