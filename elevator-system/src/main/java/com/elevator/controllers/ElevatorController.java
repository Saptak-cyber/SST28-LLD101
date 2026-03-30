package com.elevator.controllers;

import com.elevator.enums.Direction;
import com.elevator.interfaces.IRequestHandler;
import com.elevator.models.Elevator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main controller coordinating elevator system
 * SRP: Single responsibility - coordinates elevators only
 * DIP: Depends on IRequestHandler abstraction, not concrete implementation
 */
public class ElevatorController {
    private final List<Elevator> elevators;
    private final IRequestHandler requestHandler;

    /**
     * Constructor with dependency injection
     * DIP: RequestHandler is injected, not created internally
     */
    public ElevatorController(IRequestHandler requestHandler) {
        this.elevators = new ArrayList<>();
        this.requestHandler = requestHandler;
    }

    /**
     * Add an elevator to the system
     */
    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    /**
     * Request an elevator to a floor
     */
    public void requestElevator(int floor, Direction direction) {
        System.out.println("\n=== Request: Floor " + floor + ", Direction " + direction + " ===");
        requestHandler.handleRequest(elevators, floor, direction);
    }

    /**
     * Get all elevators (immutable view)
     */
    public List<Elevator> getElevators() {
        return Collections.unmodifiableList(elevators);
    }

    /**
     * Get elevator by ID
     */
    public Elevator getElevatorById(int id) {
        return elevators.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get system status
     */
    public void printSystemStatus() {
        System.out.println("\n=== ELEVATOR SYSTEM STATUS ===");
        for (Elevator elevator : elevators) {
            System.out.println(elevator);
        }
    }
}
