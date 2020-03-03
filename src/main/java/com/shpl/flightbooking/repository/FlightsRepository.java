package com.shpl.flightbooking.repository;

import com.shpl.flightbooking.entity.FlightEntity;
import com.shpl.flightbooking.entity.FlightKey;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface FlightsRepository extends CrudRepository<FlightEntity, FlightKey> {

}
