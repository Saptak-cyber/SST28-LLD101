package com.elevator.strategies;

import com.elevator.enums.ElevatorState;
import com.elevator.interfaces.IElevatorStrategy;
import com.elevator.models.Elevator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Strategy: Select nearest available elevator
 * OCP: Implements IElevatorStrategy - can be extended without modifying existing code
 * LSP: Can be substituted for any IElevatorStrategy implementation
 */
public class NearestElevatorStrategy implements IElevatorStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, int requestedFloor) {
        // Filter out elevators in maintenance
        List<Elevator> available = elevators.stream()
                .filter(e -> e.getState() != ElevatorState.MAINTENANCE)
                .collect(Collectors.toList());

        if (available.isEmpty()) {
            return null;
        }

        // Find nearest elevator
        Elevator nearest = available.get(0);
        int minDistance = Math.abs(nearest.getCurrentFloor() - requestedFloor);

        for (Elevator elevator : available) {
            int distance = Math.abs(elevator.getCurrentFloor() - requestedFloor);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = elevator;
            }
        }

        return nearest;
    }
}
