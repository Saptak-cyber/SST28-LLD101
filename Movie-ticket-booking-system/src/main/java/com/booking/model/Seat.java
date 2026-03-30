package com.booking.model;

// Single Responsibility: Manages seat state and behavior only
public class Seat {
    private final String id;
    private final String row;
    private final int number;
    private final SeatType type;
    private SeatStatus status;

    public Seat(String id, String row, int number, SeatType type) {
        this.id = id;
        this.row = row;
        this.number = number;
        this.type = type;
        this.status = SeatStatus.AVAILABLE;
    }

    public void book() {
        if (status != SeatStatus.AVAILABLE) {
            throw new IllegalStateException("Seat " + id + " is not available");
        }
        this.status = SeatStatus.BOOKED;
    }

    public void reserve() {
        if (status != SeatStatus.AVAILABLE) {
            throw new IllegalStateException("Seat " + id + " is not available");
        }
        this.status = SeatStatus.RESERVED;
    }

    public void release() {
        this.status = SeatStatus.AVAILABLE;
    }

    public boolean isAvailable() {
        return status == SeatStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public String getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public SeatType getType() {
        return type;
    }

    public SeatStatus getStatus() {
        return status;
    }
}
