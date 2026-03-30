package com.booking.interfaces;

// Interface Segregation & Dependency Inversion: Payment abstraction
public interface PaymentProcessor {
    PaymentResult processPayment(double amount, String customerId);
    PaymentResult refund(String transactionId, double amount);
}
