package com.booking.pricing;

import com.booking.interfaces.PricingStrategy;
import com.booking.model.Seat;
import com.booking.model.SeatType;
import com.booking.model.Showtime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Open/Closed: Premium time pricing strategy
public class PremiumTimePricing implements PricingStrategy {
    private final Map<SeatType, Double> basePrices;

    public PremiumTimePricing() {
        basePrices = new HashMap<>();
        basePrices.put(SeatType.REGULAR, 10.0);
        basePrices.put(SeatType.PREMIUM, 15.0);
        basePrices.put(SeatType.VIP, 25.0);
    }

    @Override
    public double calculatePrice(List<Seat> seats, Showtime showtime) {
        double basePrice = seats.stream()
                               .mapToDouble(seat -> basePrices.get(seat.getType()))
                               .sum();

        int hour = showtime.getStartTime().getHour();
        boolean isPrimeTime = hour >= 18 && hour <= 22; // 6 PM to 10 PM

        return isPrimeTime ? basePrice * 1.3 : basePrice; // 30% markup for prime time
    }
}
