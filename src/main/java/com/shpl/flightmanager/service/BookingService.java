package com.shpl.flightmanager.service;


import com.google.common.collect.Lists;
import com.shpl.flightmanager.dto.FlightBookingResult;
import com.shpl.flightmanager.dto.FlightBookingStatus;
import com.shpl.flightmanager.dto.FlightExistsDto;
import com.shpl.flightmanager.dto.FlightRemainingSeats;
import com.shpl.flightmanager.entity.Flight;
import com.shpl.flightmanager.mapper.FlightMapper;
import com.shpl.flightmanager.repository.FlightRepository;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;

    private final PnrService pnrService;

    public Mono<FlightBookingResult> saveNewBooking(final String flightId) {

        final String pnr = pnrService.generatePnr();

        return flightRepository.findById(flightId)
                .filter(this::checkIfAvailableSeats)
                .flatMap(flight -> saveBooking(flight, pnr))
                .map(__ -> getConfirmedBooking(pnr))
                .defaultIfEmpty(getUnconfirmedBooking());
    }

    public Mono<FlightRemainingSeats> getBookingInfo(final String flightId) {
        return flightRepository.findById(flightId)
                .map(flightMapper::flightToFlightRemainingSeats)
                .defaultIfEmpty(FlightRemainingSeats.builder().build());
    }

    public Mono<FlightExistsDto> isFlightAvailable(final String flightId) {
        return flightRepository.findById(flightId)
                .map(flight -> FlightExistsDto.builder().isFlightAvailable(Optional.ofNullable(flight).isPresent()).build());
    }

    private boolean checkIfAvailableSeats(final Flight flight) {
        return flight.getTotalSeatsAvailable() > flight.getSoldSeats();
    }

    private Mono<Flight> saveBooking(final Flight flight, final String pnr) {

        return Option.of(flight)
                .map(flightMap -> flightMap.withSoldSeats(flight.getSoldSeats() + 1))
                .map(flightMap -> flightMap.withPassengersPnr(addPnrToFlight(flightMap.getPassengersPnr(), pnr)))
                .map(flightRepository::save)
                .get();
    }

    private List<String> addPnrToFlight(final List<String> passengers, final String pnr) {
        return Option.of(passengers)
                .peek(__ -> passengers.add(pnr))
                .getOrElse(Lists.newArrayList(pnr));
    }

    private FlightBookingResult getConfirmedBooking(final String pnr) {
        return FlightBookingResult.builder().flightBookingStatus(FlightBookingStatus.CONFIRMED).pnr(pnr).build();
    }

    private FlightBookingResult getUnconfirmedBooking() {
        return FlightBookingResult.builder().flightBookingStatus(FlightBookingStatus.REFUSED).build();
    }

}
