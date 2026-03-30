package com.elevator.handlers;

import com.elevator.enums.Direction;
import com.elevator.interfaces.IElevatorStrategy;
import com.elevator.interfaces.IRequestHandler;
import com.elevator.models.Elevator;
import java.util.List;

/**
 * Handles elevator requests using injected strategy
 * SRP: Single responsibility - handles request processing only
 * DIP: Depends on IElevatorStrategy abstraction, not concrete implementation
 * OCP: Can work with any strategy without modification
 */
public class ElevatorRequestHandler implements IRequestHandler {
    private IElevatorStrategy strategy;

    /**
     * Constructor with dependency injection
     * DIP: Strategy is injected, not created internally
     */
    public ElevatorRequestHandler(IElevatorStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Elevator handleRequest(List<Elevator> elevators, int floor, Direction direction) {
        Elevator selected = strategy.selectElevator(elevators, floor);

        if (selected != null) {
            System.out.println("Selected Elevator " + selected.getId() + " for floor " + floor);
            selected.moveToFloor(floor);
        } else {
            System.out.println("No available elevator");
        }

        return selected;
    }

    /**
     * Change strategy at runtime
     * Strategy Pattern: Allows dynamic strategy switching
     * OCP: New strategies can be added without modifying this class
     */
    public void setStrategy(IElevatorStrategy strategy) {
        this.strategy = strategy;
    }

    public IElevatorStrategy getStrategy() {
        return strategy;
    }
}
