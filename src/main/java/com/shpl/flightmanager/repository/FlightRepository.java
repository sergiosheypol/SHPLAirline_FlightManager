package com.shpl.flightmanager.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.shpl.flightmanager.dto.FlightKeysDto;
import com.shpl.flightmanager.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class FlightRepository {

    private final DynamoDBMapper mapper;

    public Mono<Flight> saveOrUpdate(final Flight flight) {
        return Mono.fromCallable(() -> {
            mapper.save(flight);
            return flight;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Flight> find(final FlightKeysDto flightKeysDto) {
        return Mono.fromCallable(() -> mapper.load(Flight.class, flightKeysDto.getId(), flightKeysDto.getIataCode()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Flight> delete(final FlightKeysDto flightKeysDto) {
        return find(flightKeysDto).map(flight -> {
            mapper.delete(flight);
            return flight;
        });
    }

}
