package com.booking.pricing;

import com.booking.interfaces.PricingStrategy;
import com.booking.model.Seat;
import com.booking.model.SeatType;
import com.booking.model.Showtime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Open/Closed: Regular pricing strategy
public class RegularPricing implements PricingStrategy {
    private final Map<SeatType, Double> basePrices;

    public RegularPricing() {
        basePrices = new HashMap<>();
        basePrices.put(SeatType.REGULAR, 10.0);
        basePrices.put(SeatType.PREMIUM, 15.0);
        basePrices.put(SeatType.VIP, 25.0);
    }

    @Override
    public double calculatePrice(List<Seat> seats, Showtime showtime) {
        return seats.stream()
                   .mapToDouble(seat -> basePrices.get(seat.getType()))
                   .sum();
    }
}
