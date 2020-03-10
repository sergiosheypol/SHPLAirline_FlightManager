package com.shpl.flightmanager.service;

import com.shpl.flightmanager.dto.FlightDto;
import com.shpl.flightmanager.dto.FlightInfoResponseDto;
import com.shpl.flightmanager.dto.FlightKeysDto;
import com.shpl.flightmanager.dto.FlightPushDto;
import com.shpl.flightmanager.dto.FlightRemainingSeats;
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

    public Mono<FlightRemainingSeats> getBookingInfo(FlightKeysDto keys) {
        return flightRepository.find(keys)
                .map(flightMapper::flightToFlightRemainingSeats)
                .defaultIfEmpty(FlightRemainingSeats.builder().build());
    }
}
