# Movie Ticket Booking System - Design Document (Java)

## Overview
A comprehensive movie ticket booking system demonstrating all five SOLID principles with clean architecture and design patterns.

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────────┐
│                    SOLID PRINCIPLES IMPLEMENTATION                   │
├─────────────────────────────────────────────────────────────────────┤
│ S - Single Responsibility Principle                                  │
│ O - Open/Closed Principle                                           │
│ L - Liskov Substitution Principle                                   │
│ I - Interface Segregation Principle                                 │
│ D - Dependency Inversion Principle                                  │
└─────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────┐
│                          INTERFACES LAYER                             │
└──────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐
│   <<interface>>      │
│   PaymentProcessor   │
├──────────────────────┤
│ + processPayment()   │
│ + refund()          │
└──────────────────────┘
         △
         │
    ┌────┴────┬──────────────┬──────────────┐
    │         │              │              │
┌───┴────┐ ┌─┴─────┐  ┌─────┴──────┐ ┌────┴────┐
│Credit  │ │Debit  │  │   Wallet   │ │  More   │
│Card    │ │Card   │  │  Payment   │ │ Payment │
│Payment │ │Payment│  │            │ │ Methods │
└────────┘ └───────┘  └────────────┘ └─────────┘

┌──────────────────────┐
│   <<interface>>      │
│   NotificationSender │
├──────────────────────┤
│ + send()            │
└──────────────────────┘
         △
         │
    ┌────┴────┬──────────┬──────────┐
    │         │          │          │
┌───┴────┐ ┌─┴─────┐ ┌──┴──────┐ ┌─┴────┐
│Email   │ │SMS    │ │  Push   │ │ More │
│Notif.  │ │Notif. │ │  Notif. │ │      │
└────────┘ └───────┘ └─────────┘ └──────┘

┌──────────────────────┐
│   <<interface>>      │
│   PricingStrategy    │
├──────────────────────┤
│ + calculatePrice()   │
└──────────────────────┘
         △
         │
    ┌────┴────┬──────────┬──────────┐
    │         │          │          │
┌───┴────┐ ┌─┴─────┐ ┌──┴──────┐ ┌─┴────┐
│Regular │ │Weekend│ │Premium  │ │ More │
│Pricing │ │Pricing│ │  Time   │ │      │
└────────┘ └───────┘ └─────────┘ └──────┘

┌──────────────────────┐
│   <<interface>>      │
│   SeatRepository     │
├──────────────────────┤
│ + findAvailable()    │
│ + findById()        │
│ + save()            │
│ + saveAll()         │
└──────────────────────┘
         △
         │
┌────────┴─────────────┐
│  InMemorySeat        │
│  Repository          │
└──────────────────────┘

┌──────────────────────────────────────────────────────────────────────┐
│                          DOMAIN MODEL LAYER                           │
└──────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐
│       Movie          │
├──────────────────────┤
│ - id: String         │
│ - title: String      │
│ - duration: int      │
│ - genre: String      │
│ - rating: String     │
├──────────────────────┤
│ + getId()           │
│ + getTitle()        │
│ + getDuration()     │
└──────────────────────┘

┌──────────────────────┐
│      Showtime        │
├──────────────────────┤
│ - id: String         │
│ - movie: Movie       │
│ - startTime: Date    │
│ - screen: String     │
├──────────────────────┤
│ + getEndTime()      │
└──────────────────────┘

┌──────────────────────┐
│        Seat          │
├──────────────────────┤
│ - id: String         │
│ - row: String        │
│ - number: int        │
│ - type: SeatType     │
│ - status: SeatStatus │
├──────────────────────┤
│ + book()            │
│ + reserve()         │
│ + release()         │
│ + isAvailable()     │
└──────────────────────┘

┌──────────────────────┐
│      Customer        │
├──────────────────────┤
│ - id: String         │
│ - name: String       │
│ - email: String      │
│ - phone: String      │
└──────────────────────┘

┌──────────────────────┐
│      Booking         │
├──────────────────────┤
│ - id: String         │
│ - showtime: Showtime │
│ - seats: List<Seat>  │
│ - customer: Customer │
│ - totalPrice: double │
│ - status: Status     │
├──────────────────────┤
│ + confirm()         │
│ + cancel()          │
└──────────────────────┘

┌──────────────────────────────────────────────────────────────────────┐
│                          SERVICE LAYER                                │
└──────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────┐
│              BookingService                          │
├──────────────────────────────────────────────────────┤
│ - seatRepository: SeatRepository                     │
│ - paymentProcessor: PaymentProcessor                 │
│ - notificationSender: NotificationSender             │
│ - pricingStrategy: PricingStrategy                   │
├──────────────────────────────────────────────────────┤
│ + createBooking()                                    │
│ + confirmPayment()                                   │
│ + cancelBooking()                                    │
└──────────────────────────────────────────────────────┘
```

## SOLID Principles Explained

### 1. Single Responsibility Principle (SRP)
**"A class should have one, and only one, reason to change"**

Each class has a single, well-defined responsibility:

- `Movie`: Manages movie information only
- `Seat`: Handles seat state transitions (available → reserved → booked)
- `Booking`: Represents booking entity and its lifecycle
- `BookingService`: Orchestrates the booking workflow
- `CreditCardPayment`: Processes credit card payments only
- `EmailNotification`: Sends email notifications only
- `RegularPricing`: Calculates regular pricing only

**Benefits:**
- Easy to understand and maintain
- Changes to one responsibility don't affect others
- Better testability

### 2. Open/Closed Principle (OCP)
**"Software entities should be open for extension, but closed for modification"**

The system is extensible without modifying existing code:

**Adding new payment methods:**
```java
// No need to modify existing code
public class PayPalPayment implements PaymentProcessor {
    // New implementation
}
```

**Adding new pricing strategies:**
```java
public class HolidayPricing implements PricingStrategy {
    // New pricing logic
}
```

**Adding new notification channels:**
```java
public class WhatsAppNotification implements NotificationSender {
    // New notification method
}
```

**Benefits:**
- System grows without breaking existing functionality
- Reduces risk of introducing bugs
- Promotes code reusability

### 3. Liskov Substitution Principle (LSP)
**"Objects should be replaceable with instances of their subtypes without altering correctness"**

Any implementation can substitute its interface:

```java
// All these are valid substitutions
PaymentProcessor payment = new CreditCardPayment();
payment = new DebitCardPayment();
payment = new WalletPayment();

NotificationSender notifier = new EmailNotification();
notifier = new SMSNotification();
notifier = new PushNotification();

PricingStrategy pricing = new RegularPricing();
pricing = new WeekendPricing();
pricing = new PremiumTimePricing();
```

**Benefits:**
- Implementations are truly interchangeable
- Polymorphism works correctly
- No unexpected behavior when swapping implementations

### 4. Interface Segregation Principle (ISP)
**"Clients should not be forced to depend on interfaces they don't use"**

Interfaces are focused and specific:

- `PaymentProcessor`: Only payment-related methods
- `NotificationSender`: Only notification sending
- `PricingStrategy`: Only price calculation
- `SeatRepository`: Only seat data access

**No fat interfaces:**
```java
// GOOD: Focused interfaces
interface PaymentProcessor {
    PaymentResult processPayment(double amount, String customerId);
    PaymentResult refund(String transactionId, double amount);
}

// BAD: Fat interface (avoided)
interface PaymentAndNotificationAndPricing {
    PaymentResult processPayment(...);
    void sendNotification(...);
    double calculatePrice(...);
}
```

**Benefits:**
- Classes implement only what they need
- Reduces coupling
- Easier to understand and implement

### 5. Dependency Inversion Principle (DIP)
**"Depend on abstractions, not concretions"**

High-level modules depend on abstractions:

```java
public class BookingService {
    // Depends on interfaces, not concrete classes
    private final SeatRepository seatRepository;
    private final PaymentProcessor paymentProcessor;
    private final NotificationSender notificationSender;
    private final PricingStrategy pricingStrategy;

    // Dependencies injected via constructor
    public BookingService(
        SeatRepository seatRepository,
        PaymentProcessor paymentProcessor,
        NotificationSender notificationSender,
        PricingStrategy pricingStrategy
    ) {
        this.seatRepository = seatRepository;
        this.paymentProcessor = paymentProcessor;
        this.notificationSender = notificationSender;
        this.pricingStrategy = pricingStrategy;
    }
}
```

**Benefits:**
- Easy to test with mocks
- Flexible configuration
- Loose coupling between components
- Easy to swap implementations

## Design Patterns Used

1. **Strategy Pattern**: For pricing strategies
2. **Repository Pattern**: For data access abstraction
3. **Dependency Injection**: For loose coupling
4. **Factory Pattern**: Can be added for object creation

## Package Structure

```
com.booking
├── interfaces/          # Abstractions (DIP, ISP)
│   ├── PaymentProcessor
│   ├── NotificationSender
│   ├── PricingStrategy
│   └── SeatRepository
├── model/              # Domain entities (SRP)
│   ├── Movie
│   ├── Showtime
│   ├── Seat
│   ├── Customer
│   └── Booking
├── payment/            # Payment implementations (OCP, LSP)
│   ├── CreditCardPayment
│   ├── DebitCardPayment
│   └── WalletPayment
├── notification/       # Notification implementations (OCP, LSP)
│   ├── EmailNotification
│   ├── SMSNotification
│   └── PushNotification
├── pricing/            # Pricing implementations (OCP, LSP)
│   ├── RegularPricing
│   ├── WeekendPricing
│   └── PremiumTimePricing
├── repository/         # Data access implementations (DIP)
│   └── InMemorySeatRepository
├── service/            # Business logic (SRP, DIP)
│   └── BookingService
└── Main.java           # Application entry point
```

## How to Run

```bash
# Compile
javac -d bin src/main/java/com/booking/**/*.java

# Run
java -cp bin com.booking.Main
```

## Extensibility Examples

### Adding a new payment method:
1. Create class implementing `PaymentProcessor`
2. No changes to existing code needed
3. Inject into `BookingService`

### Adding a new pricing strategy:
1. Create class implementing `PricingStrategy`
2. No changes to existing code needed
3. Inject into `BookingService`

### Adding a new notification channel:
1. Create class implementing `NotificationSender`
2. No changes to existing code needed
3. Inject into `BookingService`

## Testing Benefits

Thanks to SOLID principles:
- Mock interfaces for unit testing
- Test each component in isolation
- Easy to create test doubles
- No need for complex test setups

```java
// Example: Easy to test with mocks
@Test
public void testBookingCreation() {
    SeatRepository mockRepo = mock(SeatRepository.class);
    PaymentProcessor mockPayment = mock(PaymentProcessor.class);
    NotificationSender mockNotifier = mock(NotificationSender.class);
    PricingStrategy mockPricing = mock(PricingStrategy.class);
    
    BookingService service = new BookingService(
        mockRepo, mockPayment, mockNotifier, mockPricing
    );
    
    // Test in isolation
}
```

## Conclusion

This design demonstrates how SOLID principles create:
- Maintainable code
- Extensible architecture
- Testable components
- Loosely coupled system
- Clear separation of concerns
