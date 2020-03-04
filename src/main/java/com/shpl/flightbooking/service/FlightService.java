package com.shpl.flightbooking.service;

import com.shpl.flightbooking.dto.FlightDto;
import com.shpl.flightbooking.entity.FlightEntity;
import com.shpl.flightbooking.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightEntity saveFlight(FlightDto flightDto) {
        return flightRepository.save(mapFlightEntity(flightDto));
    }

    public FlightDto getFlight(String id) {
        return mapFlightDto(flightRepository.findById(id).orElse(FlightEntity.builder().build()));
    }


    private FlightEntity mapFlightEntity(FlightDto flightDto) {
        return FlightEntity.builder()
                .id(flightDto.getId())
                .iataCode(flightDto.getIataCode())
                .arrivalAirport(flightDto.getArrivalAirport())
                .departureAirport(flightDto.getDepartureAirport())
                .arrivalDate(flightDto.getArrivalDate())
                .departureDate(flightDto.getDepartureDate())
                .connectingAirport(flightDto.getConnectingAirport())
                .soldSeats(flightDto.getSoldSeats())
                .totalSeatsAvailable(flightDto.getTotalSeatsAvailable())
                .build();
    }

    private FlightDto mapFlightDto(FlightEntity flightEntity) {
        return FlightDto.builder()
                .arrivalAirport(flightEntity.getArrivalAirport())
                .arrivalDate(flightEntity.getArrivalDate())
                .connectingAirport(flightEntity.getConnectingAirport())
                .departureAirport(flightEntity.getDepartureAirport())
                .departureDate(flightEntity.getDepartureDate())
                .iataCode(flightEntity.getIataCode())
                .id(flightEntity.getId())
                .soldSeats(flightEntity.getSoldSeats())
                .totalSeatsAvailable(flightEntity.getTotalSeatsAvailable())
                .build();
    }





}
