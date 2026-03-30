package com.booking.payment;

import com.booking.interfaces.PaymentProcessor;
import com.booking.interfaces.PaymentResult;

// Open/Closed & Liskov Substitution: Another payment implementation
public class DebitCardPayment implements PaymentProcessor {
    
    @Override
    public PaymentResult processPayment(double amount, String customerId) {
        System.out.println("Processing debit card payment of $" + amount + 
                         " for customer " + customerId);
        
        return new PaymentResult(
            true,
            "DC-" + System.currentTimeMillis(),
            "Debit card payment processed successfully"
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
