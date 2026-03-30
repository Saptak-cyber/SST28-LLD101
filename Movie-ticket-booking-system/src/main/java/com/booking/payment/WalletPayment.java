package com.booking.payment;

import com.booking.interfaces.PaymentProcessor;
import com.booking.interfaces.PaymentResult;

// Open/Closed & Liskov Substitution: Wallet payment implementation
public class WalletPayment implements PaymentProcessor {
    
    @Override
    public PaymentResult processPayment(double amount, String customerId) {
        System.out.println("Processing wallet payment of $" + amount + 
                         " for customer " + customerId);
        
        return new PaymentResult(
            true,
            "WALLET-" + System.currentTimeMillis(),
            "Wallet payment processed successfully"
        );
    }

    @Override
    public PaymentResult refund(String transactionId, double amount) {
        System.out.println("Refunding $" + amount + " to wallet for transaction " + transactionId);
        
        return new PaymentResult(
            true,
            "REF-" + transactionId,
            "Refund to wallet processed successfully"
        );
    }
}
