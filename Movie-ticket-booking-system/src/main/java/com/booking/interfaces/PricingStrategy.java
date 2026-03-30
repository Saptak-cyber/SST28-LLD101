package com.booking.interfaces;

import com.booking.model.Seat;
import com.booking.model.Showtime;
import java.util.List;

// Open/Closed & Dependency Inversion: Pricing abstraction
public interface PricingStrategy {
    double calculatePrice(List<Seat> seats, Showtime showtime);
}
