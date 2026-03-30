package com.elevator.strategies;

import com.elevator.enums.Direction;
import com.elevator.enums.ElevatorState;
import com.elevator.interfaces.IElevatorStrategy;
import com.elevator.models.Elevator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Strategy: Select elevator with least usage for load balancing
 * OCP: Another strategy demonstrating extensibility
 * LSP: Can be substituted for any IElevatorStrategy implementation
 */
public class LoadBalancingStrategy implements IElevatorStrategy {
    private final Map<Integer, Integer> usageCount;

    public LoadBalancingStrategy() {
        this.usageCount = new HashMap<>();
    }

    @Override
    public Elevator selectElevator(List<Elevator> elevators, int requestedFloor) {
        // Filter out elevators in maintenance
        List<Elevator> available = elevators.stream()
                .filter(e -> e.getState() != ElevatorState.MAINTENANCE)
                .collect(Collectors.toList());

        if (available.isEmpty()) {
            return null;
        }

        // Select elevator with least usage
        Elevator selected = available.get(0);
        int minUsage = usageCount.getOrDefault(selected.getId(), 0);

        for (Elevator elevator : available) {
            int usage = usageCount.getOrDefault(elevator.getId(), 0);
            if (usage < minUsage) {
                minUsage = usage;
                selected = elevator;
            }
        }

        // Increment usage count
        usageCount.put(selected.getId(), minUsage + 1);

        return selected;
    }

    public Map<Integer, Integer> getUsageStatistics() {
        return new HashMap<>(usageCount);
    }
}
