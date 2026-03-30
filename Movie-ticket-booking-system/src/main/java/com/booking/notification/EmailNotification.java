package com.booking.notification;

import com.booking.interfaces.NotificationSender;
import com.booking.interfaces.NotificationMessage;

// Open/Closed & Liskov Substitution: Email notification implementation
public class EmailNotification implements NotificationSender {
    
    @Override
    public void send(NotificationMessage message) {
        System.out.println("Sending email to " + message.getRecipient());
        System.out.println("Subject: " + message.getSubject());
        System.out.println("Body: " + message.getBody());
    }
}
