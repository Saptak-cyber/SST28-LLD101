package com.booking.notification;

import com.booking.interfaces.NotificationSender;
import com.booking.interfaces.NotificationMessage;

// Open/Closed & Liskov Substitution: SMS notification implementation
public class SMSNotification implements NotificationSender {
    
    @Override
    public void send(NotificationMessage message) {
        System.out.println("Sending SMS to " + message.getRecipient());
        System.out.println("Message: " + message.getBody());
    }
}
