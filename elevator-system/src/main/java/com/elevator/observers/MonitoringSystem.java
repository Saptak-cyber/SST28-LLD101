package com.elevator.observers;

import com.elevator.enums.Direction;
import com.elevator.enums.ElevatorState;
import com.elevator.interfaces.IElevatorObserver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Monitoring system that logs elevator activity
 * SRP: Single responsibility - logging only
 * LSP: Another IElevatorObserver implementation - demonstrates substitutability
 * ISP: Implements only the methods it needs
 */
public class MonitoringSystem implements IElevatorObserver {
    private final List<String> logs;
    private final DateTimeFormatter formatter;

    public MonitoringSystem() {
        this.logs = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public void update(int elevatorId, int floor, Direction direction, ElevatorState state) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = String.format("[%s] Elevator %d: Floor %d, %s, %s",
                timestamp, elevatorId, floor, direction, state);
        logs.add(logEntry);
    }

    /**
     * Retrieve activity logs
     */
    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    /**
     * Get recent logs
     */
    public List<String> getRecentLogs(int count) {
        int size = logs.size();
        int fromIndex = Math.max(0, size - count);
        return new ArrayList<>(logs.subList(fromIndex, size));
    }

    /**
     * Clear all logs
     */
    public void clearLogs() {
        logs.clear();
    }

    /**
     * Print all logs
     */
    public void printLogs() {
        System.out.println("\n=== MONITORING SYSTEM LOGS ===");
        for (String log : logs) {
            System.out.println(log);
        }
    }
}
