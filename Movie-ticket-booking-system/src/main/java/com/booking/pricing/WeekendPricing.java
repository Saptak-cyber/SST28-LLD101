package com.booking.pricing;

import com.booking.interfaces.PricingStrategy;
import com.booking.model.Seat;
import com.booking.model.SeatType;
import com.booking.model.Showtime;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Open/Closed: Weekend pricing strategy with markup
public class WeekendPricing implements PricingStrategy {
    private final Map<SeatType, Double> basePrices;
    private final double weekendMarkup = 1.5; // 50% markup on weekends

    public WeekendPricing() {
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

        DayOfWeek day = showtime.getStartTime().getDayOfWeek();
        boolean isWeekend = day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;

        return isWeekend ? basePrice * weekendMarkup : basePrice;
    }
}
