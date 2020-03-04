package com.shpl.flightbooking.repository;

import com.shpl.flightbooking.entity.FlightEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableScan
public interface FlightRepository extends CrudRepository<FlightEntity, String> {

}
