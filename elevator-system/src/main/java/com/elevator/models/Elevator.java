package com.elevator.models;

import com.elevator.enums.Direction;
import com.elevator.enums.ElevatorState;
import com.elevator.interfaces.IElevatorObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Elevator class managing state and movement
 * SRP: Single responsibility - manages only elevator state and movement
 * DIP: Depends on IElevatorObserver abstraction, not concrete implementations
 */
public class Elevator {
    private final int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;
    private final int totalFloors;
    private final List<IElevatorObserver> observers;

    public Elevator(int id, int totalFloors) {
        this.id = id;
        this.totalFloors = totalFloors;
        this.currentFloor = 1;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.STOPPED;
        this.observers = new ArrayList<>();
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public int getTotalFloors() {
        return totalFloors;
    }

    /**
     * Add an observer to receive state updates
     * Observer Pattern implementation
     */
    public void addObserver(IElevatorObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove an observer
     */
    public void removeObserver(IElevatorObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notify all observers of state change
     * Observer Pattern implementation
     */
    private void notifyObservers() {
        for (IElevatorObserver observer : observers) {
            observer.update(id, currentFloor, direction, state);
        }
    }

    /**
     * Move elevator to target floor
     * SRP: Handles only movement logic
     */
    public void moveToFloor(int targetFloor) {
        if (targetFloor < 1 || targetFloor > totalFloors) {
            throw new IllegalArgumentException("Invalid floor: " + targetFloor);
        }

        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("Elevator " + id + " is under maintenance");
            return;
        }

        if (targetFloor == currentFloor) {
            direction = Direction.IDLE;
            state = ElevatorState.STOPPED;
            notifyObservers();
            return;
        }

        // Determine direction
        direction = targetFloor > currentFloor ? Direction.UP : Direction.DOWN;
        state = ElevatorState.MOVING;
        notifyObservers();

        // Simulate movement
        int step = direction == Direction.UP ? 1 : -1;
        while (currentFloor != targetFloor) {
            currentFloor += step;
            System.out.println("Elevator " + id + " at floor " + currentFloor);
            notifyObservers();
            
            // Simulate delay
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Arrived
        direction = Direction.IDLE;
        state = ElevatorState.STOPPED;
        notifyObservers();
    }

    /**
     * Set elevator maintenance state
     */
    public void setMaintenanceMode(boolean maintenance) {
        state = maintenance ? ElevatorState.MAINTENANCE : ElevatorState.STOPPED;
        notifyObservers();
    }

    @Override
    public String toString() {
        return String.format("Elevator[id=%d, floor=%d, direction=%s, state=%s]",
                id, currentFloor, direction, state);
    }
}
