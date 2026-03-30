package com.booking.interfaces;

import com.booking.model.Seat;
import java.util.List;

// Dependency Inversion: Repository abstraction
public interface SeatRepository {
    List<Seat> findAvailableSeats(String showtimeId);
    Seat findById(String seatId);
    void save(Seat seat);
    void saveAll(List<Seat> seats);
}
