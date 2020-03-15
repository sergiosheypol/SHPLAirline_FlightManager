package com.shpl.flightmanager.service;

import com.shpl.flightmanager.entity.Flight;
import com.shpl.flightmanager.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FlightCrudService {

    private final FlightRepository flightRepository;

    //TODO: don't override existing bookings
    public Mono<Flight> saveFlight(final Flight flight) {
        return flightRepository.save(flight);
    }

    public Mono<Flight> findFlight(final String flightId) {
        return flightRepository.findById(flightId);
    }

    public Mono<Flight> deleteFlight(final String flightId) {
        return flightRepository.findById(flightId)
                .map(flight -> {
                    flightRepository.delete(flight);
                    return flight;
                })
                .defaultIfEmpty(Flight.builder().build());
    }


}
