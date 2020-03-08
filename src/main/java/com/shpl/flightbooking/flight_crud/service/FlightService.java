package com.shpl.flightbooking.flight_crud.service;

import com.shpl.flightbooking.flight_crud.dto.FlightInfoResponseDto;
import com.shpl.flightbooking.flight_crud.dto.FlightKeysDto;
import com.shpl.flightbooking.flight_crud.dto.FlightPushDto;
import com.shpl.flightbooking.flight_crud.mapper.FlightMapper;
import com.shpl.flightbooking.flight_crud.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class FlightService {
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
}
