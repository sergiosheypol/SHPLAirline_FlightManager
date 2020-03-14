package com.shpl.flightmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class FlightbookingApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlightbookingApplication.class, args);
	}
}
