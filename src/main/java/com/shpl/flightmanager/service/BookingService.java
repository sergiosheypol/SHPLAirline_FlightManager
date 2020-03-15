package com.shpl.flightmanager.service;

import com.google.common.collect.Lists;
import com.shpl.flightmanager.dto.FlightBookingResultDto;
import com.shpl.flightmanager.dto.FlightBookingStatus;
import com.shpl.flightmanager.dto.FlightExistsDto;
import com.shpl.flightmanager.dto.FlightRemainingSeatsDto;
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

    public Mono<FlightBookingResultDto> saveNewBooking(final String flightId) {

        final Mono<String> pnr = pnrService.generatePnr();

        return Mono.zip(flightRepository.findById(flightId), pnr)
                .filter(zip -> checkIfAvailableSeats(zip.getT1()))
                .map(zip -> updateFlight(zip.getT1(), zip.getT2()))
                .flatMap(flightRepository::save)
                .zipWith(pnr)
                .map(zip -> getConfirmedBooking(zip.getT2()));
    }

    public Mono<FlightRemainingSeatsDto> getBookingInfo(final String flightId) {
        return flightRepository.findById(flightId)
                .map(flightMapper::flightToFlightRemainingSeats)
                .defaultIfEmpty(FlightRemainingSeatsDto.builder().build());
    }

    public Mono<FlightExistsDto> isFlightAvailable(final String flightId) {
        return flightRepository.findById(flightId)
                .map(flight -> FlightExistsDto.builder().isFlightAvailable(Optional.ofNullable(flight).isPresent()).build())
                .defaultIfEmpty(FlightExistsDto.builder().isFlightAvailable(false).build());
    }

    private boolean checkIfAvailableSeats(final Flight flight) {
        return flight.getTotalSeatsAvailable() > flight.getSoldSeats();
    }

    private Flight updateFlight(final Flight flight, final String pnr) {
        return Option.of(flight)
                .map(flightMap -> flightMap.withSoldSeats(flight.getSoldSeats() + 1))
                .map(flightMap -> flightMap.withPassengersPnr(addPnrToFlight(flightMap.getPassengersPnr(), pnr)))
                .get();
    }

    private List<String> addPnrToFlight(final List<String> passengers, final String pnr) {
        return Option.of(passengers)
                .peek(__ -> passengers.add(pnr))
                .getOrElse(Lists.newArrayList(pnr));
    }

    private FlightBookingResultDto getConfirmedBooking(final String pnr) {
        return FlightBookingResultDto.builder().flightBookingStatus(FlightBookingStatus.CONFIRMED).pnr(pnr).build();
    }

    private FlightBookingResultDto getUnconfirmedBooking() {
        return FlightBookingResultDto.builder().flightBookingStatus(FlightBookingStatus.REFUSED).build();
    }

}
