package com.booking.notification;

import com.booking.interfaces.NotificationSender;
import com.booking.interfaces.NotificationMessage;

// Open/Closed & Liskov Substitution: Push notification implementation
public class PushNotification implements NotificationSender {
    
    @Override
    public void send(NotificationMessage message) {
        System.out.println("Sending push notification to " + message.getRecipient());
        System.out.println("Title: " + message.getSubject());
        System.out.println("Body: " + message.getBody());
    }
}
