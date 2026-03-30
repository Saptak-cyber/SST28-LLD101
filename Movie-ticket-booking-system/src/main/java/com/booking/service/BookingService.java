package com.booking.service;

import com.booking.interfaces.*;
import com.booking.model.*;
import java.util.ArrayList;
import java.util.List;

// Single Responsibility & Dependency Inversion: Booking orchestration
public class BookingService {
    private final SeatRepository seatRepository;
    private final PaymentProcessor paymentProcessor;
    private final NotificationSender notificationSender;
    private final PricingStrategy pricingStrategy;

    public BookingService(SeatRepository seatRepository,
                         PaymentProcessor paymentProcessor,
                         NotificationSender notificationSender,
                         PricingStrategy pricingStrategy) {
        this.seatRepository = seatRepository;
        this.paymentProcessor = paymentProcessor;
        this.notificationSender = notificationSender;
        this.pricingStrategy = pricingStrategy;
    }

    public Booking createBooking(Showtime showtime, List<String> seatIds, Customer customer) {
        // Retrieve seats
        List<Seat> seats = new ArrayList<>();
        for (String seatId : seatIds) {
            Seat seat = seatRepository.findById(seatId);
            if (seat == null) {
                throw new IllegalArgumentException("Seat " + seatId + " not found");
            }
            if (!seat.isAvailable()) {
                throw new IllegalStateException("Seat " + seatId + " is not available");
            }
            seats.add(seat);
        }

        // Calculate price using strategy
        double totalPrice = pricingStrategy.calculatePrice(seats, showtime);

        // Create booking
        Booking booking = new Booking(
            "BK-" + System.currentTimeMillis(),
            showtime,
            seats,
            customer,
            totalPrice
        );

        // Reserve seats
        for (Seat seat : seats) {
            seat.reserve();
        }
        seatRepository.saveAll(seats);

        return booking;
    }

    public void confirmPayment(Booking booking) {
        // Process payment
        PaymentResult paymentResult = paymentProcessor.processPayment(
            booking.getTotalPrice(),
            booking.getCustomer().getId()
        );

        if (!paymentResult.isSuccess()) {
            // Release seats if payment fails
            for (Seat seat : booking.getSeats()) {
                seat.release();
            }
            seatRepository.saveAll(booking.getSeats());
            throw new RuntimeException("Payment failed: " + paymentResult.getMessage());
        }

        // Confirm booking
        booking.confirm();
        for (Seat seat : booking.getSeats()) {
            seat.book();
        }
        seatRepository.saveAll(booking.getSeats());

        // Send confirmation notification
        NotificationMessage message = new NotificationMessage(
            booking.getCustomer().getEmail(),
            "Booking Confirmed",
            String.format("Your booking %s for %s has been confirmed. Total: $%.2f",
                         booking.getId(),
                         booking.getShowtime().getMovie().getTitle(),
                         booking.getTotalPrice())
        );
        notificationSender.send(message);
    }

    public void cancelBooking(Booking booking) {
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is already cancelled");
        }

        // Release seats
        for (Seat seat : booking.getSeats()) {
            seat.release();
        }
        seatRepository.saveAll(booking.getSeats());

        // Refund if payment was made
        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            paymentProcessor.refund("BK-" + booking.getId(), booking.getTotalPrice());
        }

        // Cancel booking
        booking.cancel();

        // Send cancellation notification
        NotificationMessage message = new NotificationMessage(
            booking.getCustomer().getEmail(),
            "Booking Cancelled",
            String.format("Your booking %s has been cancelled. Refund will be processed if applicable.",
                         booking.getId())
        );
        notificationSender.send(message);
    }
}
