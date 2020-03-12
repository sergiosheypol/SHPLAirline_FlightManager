package com.shpl.flightmanager.service;

import com.google.common.collect.Lists;
import com.shpl.flightmanager.dto.*;
import com.shpl.flightmanager.entity.Flight;
import com.shpl.flightmanager.mapper.FlightMapper;
import com.shpl.flightmanager.repository.FlightRepository;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;

    private final PnrService pnrService;

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

    public Mono<FlightBookingResult> saveNewBooking(FlightKeysDto flightKeysDto) {
        return flightRepository.find(flightKeysDto)
                .filter(this::checkIfAvailableSeats)
                .map(this::saveBooking)
                .defaultIfEmpty(getUnconfirmedBooking());

    }

    public Mono<FlightRemainingSeats> getBookingInfo(FlightKeysDto keys) {
        return flightRepository.find(keys)
                .map(flightMapper::flightToFlightRemainingSeats)
                .defaultIfEmpty(FlightRemainingSeats.builder().build());
    }

    private boolean checkIfAvailableSeats(Flight flight) {
        return flight.getTotalSeatsAvailable() > flight.getSoldSeats();
    }

    private FlightBookingResult saveBooking(Flight flight) {

        String pnr = pnrService.generatePnr();

        return Option.of(flight)
                .map(flightMap -> flightMap.withSoldSeats(flight.getSoldSeats() + 1))
                .map(flightMap -> flightMap.withPassengersPnr(addPnrToFlight(flightMap.getPassengersPnr(), pnr)))
                .map(flightRepository::saveOrUpdate)
                .map(__ -> getConfirmedBooking(pnr))
                .get();
    }

    private List<String> addPnrToFlight(List<String> passengers, String pnr) {
        return Option.of(passengers)
                .peek(__ -> passengers.add(pnr))
                .getOrElse(Lists.newArrayList(pnr));
    }

    private FlightBookingResult getConfirmedBooking(String pnr) {
        return FlightBookingResult.builder().flightBookingStatus(FlightBookingStatus.CONFIRMED).pnr(pnr).build();
    }

    private FlightBookingResult getUnconfirmedBooking() {
        return FlightBookingResult.builder().flightBookingStatus(FlightBookingStatus.REFUSED).build();
    }


}
