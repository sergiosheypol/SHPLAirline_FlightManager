package com.shpl.flightmanager.service;

import com.shpl.flightmanager.dto.FlightBookingResult;
import com.shpl.flightmanager.dto.FlightBookingStatus;
import com.shpl.flightmanager.dto.FlightInfoResponseDto;
import com.shpl.flightmanager.dto.FlightKeysDto;
import com.shpl.flightmanager.dto.FlightPushDto;
import com.shpl.flightmanager.dto.FlightRemainingSeats;
import com.shpl.flightmanager.entity.Flight;
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
                .map(this::increaseSoldSeats)
                .map(this::saveBooking);

    }

    public Mono<FlightRemainingSeats> getBookingInfo(FlightKeysDto keys) {
        return flightRepository.find(keys)
                .map(flightMapper::flightToFlightRemainingSeats)
                .defaultIfEmpty(FlightRemainingSeats.builder().build());
    }

    private boolean checkIfAvailableSeats(Flight flight) {
        return flight.getTotalSeatsAvailable() > flight.getSoldSeats();
    }

    private Flight increaseSoldSeats(Flight flight) {
        return flight.withSoldSeats(flight.getSoldSeats() + 1);
    }

    private FlightBookingResult saveBooking(Flight flight) {

        String pnr = pnrService.generatePnr();

        flight.getPassengers().getPassengersPnr().add(pnr);

        flightRepository.saveOrUpdate(flight);

        return FlightBookingResult.builder()
                .flightBookingStatus(FlightBookingStatus.CONFIRMED)
                .pnr(pnr)
                .build();
    }


}
