package com.shpl.flightmanager.repository;

import com.shpl.flightmanager.entity.Flight;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends ReactiveMongoRepository<Flight, String> {
}
