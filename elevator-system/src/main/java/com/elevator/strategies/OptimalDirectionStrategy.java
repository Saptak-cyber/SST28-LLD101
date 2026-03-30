package com.elevator.strategies;

import com.elevator.enums.Direction;
import com.elevator.enums.ElevatorState;
import com.elevator.interfaces.IElevatorStrategy;
import com.elevator.models.Elevator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Alternative strategy: Select elevator already moving in requested direction
 * OCP: New strategy added without modifying existing code
 * LSP: Can be substituted for any IElevatorStrategy implementation
 */
public class OptimalDirectionStrategy implements IElevatorStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, int requestedFloor) {
        // Filter out elevators in maintenance
        List<Elevator> available = elevators.stream()
                .filter(e -> e.getState() != ElevatorState.MAINTENANCE)
                .collect(Collectors.toList());

        if (available.isEmpty()) {
            return null;
        }

        // Prefer idle elevators first
        List<Elevator> idle = available.stream()
                .filter(e -> e.getDirection() == Direction.IDLE)
                .collect(Collectors.toList());

        if (!idle.isEmpty()) {
            // Return nearest idle elevator
            Elevator nearest = idle.get(0);
            int minDistance = Math.abs(nearest.getCurrentFloor() - requestedFloor);

            for (Elevator elevator : idle) {
                int distance = Math.abs(elevator.getCurrentFloor() - requestedFloor);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = elevator;
                }
            }
            return nearest;
        }

        // If no idle elevators, return first available
        return available.get(0);
    }
}
