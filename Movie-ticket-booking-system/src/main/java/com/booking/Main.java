package com.booking;

import com.booking.interfaces.*;
import com.booking.model.*;
import com.booking.notification.EmailNotification;
import com.booking.payment.CreditCardPayment;
import com.booking.pricing.WeekendPricing;
import com.booking.repository.InMemorySeatRepository;
import com.booking.service.BookingService;
import java.time.LocalDateTime;
import java.util.Arrays;

// Demonstration of SOLID principles in action
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Movie Ticket Booking System ===\n");

        // Setup repository and add seats
        SeatRepository seatRepository = new InMemorySeatRepository();
        setupSeats(seatRepository);

        // Dependency Injection: Inject dependencies into BookingService
        PaymentProcessor paymentProcessor = new CreditCardPayment();
        NotificationSender notificationSender = new EmailNotification();
        PricingStrategy pricingStrategy = new WeekendPricing();

        BookingService bookingService = new BookingService(
            seatRepository,
            paymentProcessor,
            notificationSender,
            pricingStrategy
        );

        // Create movie and showtime
        Movie movie = new Movie("M1", "Inception", 148, "Sci-Fi", "PG-13");
        LocalDateTime showtime = LocalDateTime.of(2026, 3, 29, 19, 30);
        Showtime movieShowtime = new Showtime("ST1", movie, showtime, "Screen 1");

        // Create customer
        Customer customer = new Customer(
            "C1",
            "John Doe",
            "john.doe@email.com",
            "555-1234"
        );

        try {
            // Create booking
            System.out.println("Creating booking...");
            Booking booking = bookingService.createBooking(
                movieShowtime,
                Arrays.asList("A1", "A2"),
                customer
            );

            System.out.println("Booking created: " + booking.getId());
            System.out.println("Total price: $" + booking.getTotalPrice());
            System.out.println("Status: " + booking.getStatus() + "\n");

            // Confirm payment
            System.out.println("Confirming payment...");
            bookingService.confirmPayment(booking);
            System.out.println("Payment confirmed!");
            System.out.println("Status: " + booking.getStatus() + "\n");

            // Cancel booking
            System.out.println("Cancelling booking...");
            bookingService.cancelBooking(booking);
            System.out.println("Booking cancelled!");
            System.out.println("Status: " + booking.getStatus());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void setupSeats(SeatRepository repository) {
        repository.save(new Seat("A1", "A", 1, SeatType.REGULAR));
        repository.save(new Seat("A2", "A", 2, SeatType.REGULAR));
        repository.save(new Seat("B1", "B", 1, SeatType.PREMIUM));
        repository.save(new Seat("B2", "B", 2, SeatType.PREMIUM));
        repository.save(new Seat("C1", "C", 1, SeatType.VIP));
    }
}
