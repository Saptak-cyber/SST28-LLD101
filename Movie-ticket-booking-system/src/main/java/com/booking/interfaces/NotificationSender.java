package com.booking.interfaces;

// Interface Segregation & Dependency Inversion: Notification abstraction
public interface NotificationSender {
    void send(NotificationMessage message);
}
