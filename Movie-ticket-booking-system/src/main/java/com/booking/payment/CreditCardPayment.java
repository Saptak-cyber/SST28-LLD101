package com.booking.payment;

import com.booking.interfaces.PaymentProcessor;
import com.booking.interfaces.PaymentResult;

// Open/Closed & Liskov Substitution: Concrete payment implementation
public class CreditCardPayment implements PaymentProcessor {
    
    @Override
    public PaymentResult processPayment(double amount, String customerId) {
        System.out.println("Processing credit card payment of $" + amount + 
                         " for customer " + customerId);
        
        return new PaymentResult(
            true,
            "CC-" + System.currentTimeMillis(),
            "Credit card payment processed successfully"
        );
    }

    @Override
    public PaymentResult refund(String transactionId, double amount) {
        System.out.println("Refunding $" + amount + " to transaction " + transactionId);
        
        return new PaymentResult(
            true,
            "REF-" + transactionId,
            "Refund processed successfully"
        );
    }
}
