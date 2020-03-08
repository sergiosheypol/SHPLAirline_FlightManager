package com.shpl.flightbooking.flight.service;

import com.shpl.flightbooking.flight.dto.FlightDto;
import com.shpl.flightbooking.flight.dto.FlightInfoResponseDto;
import com.shpl.flightbooking.flight.dto.FlightKeysDto;
import com.shpl.flightbooking.flight.dto.FlightPushDto;
import com.shpl.flightbooking.flight.mapper.FlightMapper;
import com.shpl.flightbooking.flight.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class FlightCrudService {
    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;

    public Mono<FlightInfoResponseDto> saveFlight(FlightPushDto flightPushDto) {
        return flightRepository.saveOrUpdate(flightMapper.flightPushDtoToFlight(flightPushDto))
                .map(flightMapper::flightToFlightInfoResponseDto)
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }

    public Mono<FlightInfoResponseDto> findFlight(FlightKeysDto flightKeysDto) {
        return flightRepository.find(flightKeysDto)
                .map(flightMapper::flightToFlightInfoResponseDto)
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }

    public Mono<FlightInfoResponseDto> deleteFlight(FlightKeysDto flightKeysDto) {
        return flightRepository.delete(flightKeysDto)
                .map(flightMapper::flightToFlightInfoResponseDto)
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }

    public Mono<FlightDto> updateNewBooking(FlightDto flightDto) {
        return flightRepository.saveOrUpdate(flightMapper.flightDtoToFlight(flightDto))
                .map(flightMapper::flightToFlightDto)
                .defaultIfEmpty(FlightDto.builder().build());
    }
}
