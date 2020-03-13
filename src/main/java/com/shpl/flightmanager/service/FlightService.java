package com.shpl.flightmanager.service;

import com.shpl.flightmanager.dto.FlightInfoResponseDto;
import com.shpl.flightmanager.dto.FlightPushDto;
import com.shpl.flightmanager.mapper.FlightMapper;
import com.shpl.flightmanager.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;

    public Mono<FlightInfoResponseDto> saveFlight(final FlightPushDto flightPushDto) {
        return flightRepository.save(flightMapper.flightPushDtoToFlight(flightPushDto))
                .map(flightMapper::flightToFlightInfoResponseDto)
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }

    public Mono<FlightInfoResponseDto> findFlight(final String flightId) {
        return flightRepository.findById(flightId)
                .map(flightMapper::flightToFlightInfoResponseDto)
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }

    public Mono<FlightInfoResponseDto> deleteFlight(final String flightId) {
        return flightRepository.findById(flightId)
                .map(flight -> {
                    flightRepository.delete(flight);
                    return flightMapper.flightToFlightInfoResponseDto(flight);
                })
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }


}
