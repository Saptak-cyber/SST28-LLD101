package com.booking.repository;

import com.booking.interfaces.SeatRepository;
import com.booking.model.Seat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Dependency Inversion: Concrete repository implementation
public class InMemorySeatRepository implements SeatRepository {
    private final Map<String, Seat> seats = new HashMap<>();

    @Override
    public List<Seat> findAvailableSeats(String showtimeId) {
        // In a real implementation, this would filter by showtime
        return seats.values().stream()
                   .filter(Seat::isAvailable)
                   .collect(Collectors.toList());
    }

    @Override
    public Seat findById(String seatId) {
        return seats.get(seatId);
    }

    @Override
    public void save(Seat seat) {
        seats.put(seat.getId(), seat);
    }

    @Override
    public void saveAll(List<Seat> seatList) {
        for (Seat seat : seatList) {
            seats.put(seat.getId(), seat);
        }
    }
}
