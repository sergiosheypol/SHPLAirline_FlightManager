package com.shpl.flightmanager.facade;

import com.shpl.flightmanager.dto.FlightBookingResultDto;
import com.shpl.flightmanager.dto.FlightBookingStatus;
import com.shpl.flightmanager.dto.FlightExistsDto;
import com.shpl.flightmanager.dto.FlightInfoResponseDto;
import com.shpl.flightmanager.dto.FlightPushDto;
import com.shpl.flightmanager.dto.FlightRemainingSeatsDto;
import com.shpl.flightmanager.mapper.FlightMapper;
import com.shpl.flightmanager.service.FlightBookingService;
import com.shpl.flightmanager.service.FlightCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightFacade {

    private final FlightBookingService bookingService;
    private final FlightCrudService flightCrudService;
    private final FlightMapper flightMapper;

    public Mono<FlightBookingResultDto> saveNewBooking(final String flightId) {
        return bookingService.saveNewBooking(flightId)
                .map(zip -> getConfirmedBooking(zip.getT2()))
                .defaultIfEmpty(FlightBookingResultDto.builder().build());
    }

    public Mono<FlightRemainingSeatsDto> getBookingInfo(final String flightId) {
        return flightCrudService.findFlight(flightId)
                .map(flightMapper::flightToFlightRemainingSeats)
                .defaultIfEmpty(FlightRemainingSeatsDto.builder().build());
    }

    public Mono<FlightExistsDto> isFlightAvailable(final String flightId) {
        return flightCrudService.findFlight(flightId)
                .map(flight -> FlightExistsDto.builder().isFlightAvailable(Optional.ofNullable(flight).isPresent()).build())
                .defaultIfEmpty(FlightExistsDto.builder().isFlightAvailable(false).build());
    }

    public Mono<FlightInfoResponseDto> saveFlight(final FlightPushDto flightPushDto) {
        return flightCrudService.saveFlight(flightMapper.flightPushDtoToFlight(flightPushDto))
                .map(flightMapper::flightToFlightInfoResponseDto)
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }

    public Mono<FlightInfoResponseDto> findFlight(final String flightId) {
        return flightCrudService.findFlight(flightId)
                .map(flightMapper::flightToFlightInfoResponseDto)
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }

    public Mono<FlightInfoResponseDto> deleteFlight(final String flightId) {
        return flightCrudService.deleteFlight(flightId)
                .map(flightMapper::flightToFlightInfoResponseDto)
                .defaultIfEmpty(FlightInfoResponseDto.builder().build());
    }

    private FlightBookingResultDto getConfirmedBooking(final String pnr) {
        return FlightBookingResultDto.builder().flightBookingStatus(FlightBookingStatus.CONFIRMED).pnr(pnr).build();
    }


}
