package com.booking.model;

import java.util.List;

// Single Responsibility: Represents a booking entity
public class Booking {
    private final String id;
    private final Showtime showtime;
    private final List<Seat> seats;
    private final Customer customer;
    private final double totalPrice;
    private BookingStatus status;

    public Booking(String id, Showtime showtime, List<Seat> seats, 
                   Customer customer, double totalPrice) {
        this.id = id;
        this.showtime = showtime;
        this.seats = seats;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.status = BookingStatus.PENDING;
    }

    public void confirm() {
        if (status != BookingStatus.PENDING) {
            throw new IllegalStateException("Only pending bookings can be confirmed");
        }
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        if (status == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }
        this.status = BookingStatus.CANCELLED;
    }

    public String getId() {
        return id;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }
}
