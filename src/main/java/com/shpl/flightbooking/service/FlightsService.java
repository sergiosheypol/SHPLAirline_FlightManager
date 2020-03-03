package com.shpl.flightbooking.service;

import com.shpl.flightbooking.dto.FlightDto;
import com.shpl.flightbooking.entity.FlightEntity;
import com.shpl.flightbooking.entity.FlightKey;
import com.shpl.flightbooking.repository.FlightsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightsService {
    private final FlightsRepository flightsRepository;

    public FlightEntity saveFlight(FlightDto flightDto) {
        return flightsRepository.save(mapFlightEntity(flightDto));
    }

    public Optional<FlightEntity> getFlight(String id, String iataCode) {
        return flightsRepository.findById(
               FlightKey.builder()
                       .id(id)
                       .iataCode(iataCode)
                       .build()
        );
    }


    private FlightEntity mapFlightEntity (FlightDto flightDto) {
        return FlightEntity.builder()
                .key(mapFlightKey(flightDto.getId(), flightDto.getIataCode()))
                .arrivalAirport(flightDto.getArrivalAirport())
                .departureAirport(flightDto.getDepartureAirport())
                .arrivalDate(flightDto.getArrivalDate())
                .departureDate(flightDto.getDepartureDate())
                .connectingAirport(flightDto.getConnectingAirport())
                .soldSeats(flightDto.getSoldSeats())
                .totalSeatsAvailable(flightDto.getTotalSeatsAvailable())
                .build();
    }

    private FlightKey mapFlightKey(String id, String iataCode) {
        return FlightKey.builder()
                .iataCode(iataCode)
                .id(id)
                .build();
    }



}
