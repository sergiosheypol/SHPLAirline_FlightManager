package com.shpl.flightbooking.booking.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private String pnr;
    private String flightId;
    private String price;
    private LocalDateTime dateOfBooking;
}
