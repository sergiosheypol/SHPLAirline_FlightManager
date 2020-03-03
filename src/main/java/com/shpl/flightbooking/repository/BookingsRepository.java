package com.shpl.flightbooking.repository;

import com.shpl.flightbooking.entity.BookingEntity;
import com.shpl.flightbooking.entity.BookingKey;
import org.springframework.data.repository.CrudRepository;

public interface BookingsRepository extends CrudRepository<BookingEntity, BookingKey> {
}
