package com.shpl.flightmanager.service;

import com.google.common.collect.Lists;
import com.shpl.flightmanager.entity.Flight;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightBookingService {

    private final FlightCrudService flightCrudService;

    private final PnrService pnrService;

    public Mono<Tuple2<Flight, String>> saveNewBooking(final String flightId) {

        final Mono<String> pnr = pnrService.generatePnr();

        return Mono.zip(flightCrudService.findFlight(flightId), pnr)
                .filter(zip -> checkIfAvailableSeats(zip.getT1()))
                .map(zip -> updateFlight(zip.getT1(), zip.getT2()))
                .flatMap(flightCrudService::saveFlight)
                .zipWith(pnr);
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

}
