# Movie Ticket Booking System

A comprehensive Java implementation demonstrating all five SOLID principles with clean architecture.

## Features

- Movie and showtime management
- Seat booking with different types (Regular, Premium, VIP)
- Multiple payment methods (Credit Card, Debit Card, Wallet)
- Multiple notification channels (Email, SMS, Push)
- Flexible pricing strategies (Regular, Weekend, Premium Time)
- Complete booking lifecycle (Create → Confirm → Cancel)

## SOLID Principles

This project demonstrates:

1. **Single Responsibility Principle (SRP)** - Each class has one reason to change
2. **Open/Closed Principle (OCP)** - Open for extension, closed for modification
3. **Liskov Substitution Principle (LSP)** - Subtypes are substitutable
4. **Interface Segregation Principle (ISP)** - Focused, specific interfaces
5. **Dependency Inversion Principle (DIP)** - Depend on abstractions

See [DESIGN.md](DESIGN.md) for detailed explanation.

## Project Structure

```
src/main/java/com/booking/
├── interfaces/          # Abstractions
├── model/              # Domain entities
├── payment/            # Payment implementations
├── notification/       # Notification implementations
├── pricing/            # Pricing strategies
├── repository/         # Data access
├── service/            # Business logic
└── Main.java           # Demo application
```

## How to Run

```bash
# Compile all Java files
javac -d bin src/main/java/com/booking/**/*.java

# Run the main application
java -cp bin com.booking.Main
```

## Example Usage

```java
// Setup dependencies (Dependency Injection)
SeatRepository seatRepository = new InMemorySeatRepository();
PaymentProcessor paymentProcessor = new CreditCardPayment();
NotificationSender notificationSender = new EmailNotification();
PricingStrategy pricingStrategy = new WeekendPricing();

// Create booking service
BookingService bookingService = new BookingService(
    seatRepository,
    paymentProcessor,
    notificationSender,
    pricingStrategy
);

// Create booking
Booking booking = bookingService.createBooking(
    showtime,
    Arrays.asList("A1", "A2"),
    customer
);

// Confirm payment
bookingService.confirmPayment(booking);

// Cancel if needed
bookingService.cancelBooking(booking);
```

## Extending the System

### Add a new payment method:

```java
public class PayPalPayment implements PaymentProcessor {
    @Override
    public PaymentResult processPayment(double amount, String customerId) {
        // Implementation
    }
    
    @Override
    public PaymentResult refund(String transactionId, double amount) {
        // Implementation
    }
}
```

### Add a new pricing strategy:

```java
public class HolidayPricing implements PricingStrategy {
    @Override
    public double calculatePrice(List<Seat> seats, Showtime showtime) {
        // Custom pricing logic
    }
}
```

### Add a new notification channel:

```java
public class WhatsAppNotification implements NotificationSender {
    @Override
    public void send(NotificationMessage message) {
        // Send via WhatsApp
    }
}
```

## Benefits of This Design

- **Maintainable**: Each class has a single, clear purpose
- **Extensible**: Add new features without modifying existing code
- **Testable**: Easy to mock dependencies for unit testing
- **Flexible**: Swap implementations at runtime
- **Scalable**: Clean separation of concerns

## License

MIT License - Feel free to use for learning and projects.
