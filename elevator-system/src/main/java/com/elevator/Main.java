package com.elevator;

import com.elevator.controllers.ElevatorController;
import com.elevator.enums.Direction;
import com.elevator.handlers.ElevatorRequestHandler;
import com.elevator.interfaces.IElevatorStrategy;
import com.elevator.models.Elevator;
import com.elevator.observers.AlertSystem;
import com.elevator.observers.DisplayPanel;
import com.elevator.observers.MonitoringSystem;
import com.elevator.strategies.LoadBalancingStrategy;
import com.elevator.strategies.NearestElevatorStrategy;
import com.elevator.strategies.OptimalDirectionStrategy;

/**
 * Main class demonstrating the elevator system with SOLID principles
 * 
 * SOLID Principles Demonstrated:
 * - SRP: Each class has a single, well-defined responsibility
 * - OCP: System is open for extension (new strategies) but closed for modification
 * - LSP: All implementations are substitutable for their interfaces
 * - ISP: Small, focused interfaces that clients can implement independently
 * - DIP: High-level modules depend on abstractions, not concretions
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("ELEVATOR SYSTEM - SOLID PRINCIPLES DEMONSTRATION");
        System.out.println("=".repeat(60));

        // DIP: Create strategy (can be swapped)
        IElevatorStrategy strategy = new NearestElevatorStrategy();

        // DIP: Create request handler with injected strategy
        ElevatorRequestHandler requestHandler = new ElevatorRequestHandler(strategy);

        // DIP: Create controller with injected handler
        ElevatorController controller = new ElevatorController(requestHandler);

        // Create elevators
        Elevator elevator1 = new Elevator(1, 10);
        Elevator elevator2 = new Elevator(2, 10);
        Elevator elevator3 = new Elevator(3, 10);

        // LSP: Create observers (all are substitutable)
        DisplayPanel display = new DisplayPanel();
        MonitoringSystem monitor = new MonitoringSystem();
        AlertSystem alerts = new AlertSystem();

        // Observer Pattern: Attach observers to elevators
        for (Elevator elevator : new Elevator[]{elevator1, elevator2, elevator3}) {
            elevator.addObserver(display);
            elevator.addObserver(monitor);
            elevator.addObserver(alerts);
            controller.addElevator(elevator);
        }

        // Demonstrate basic requests
        System.out.println("\n--- Testing Nearest Elevator Strategy ---");
        controller.requestElevator(5, Direction.UP);
        controller.requestElevator(8, Direction.DOWN);
        controller.requestElevator(3, Direction.UP);

        // OCP: Change strategy at runtime without modifying existing code
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SWITCHING TO OPTIMAL DIRECTION STRATEGY");
        System.out.println("=".repeat(60));
        requestHandler.setStrategy(new OptimalDirectionStrategy());

        controller.requestElevator(7, Direction.UP);

        // OCP: Use another strategy
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SWITCHING TO LOAD BALANCING STRATEGY");
        System.out.println("=".repeat(60));
        LoadBalancingStrategy loadBalancing = new LoadBalancingStrategy();
        requestHandler.setStrategy(loadBalancing);

        controller.requestElevator(6, Direction.DOWN);
        controller.requestElevator(9, Direction.UP);

        // Show load balancing statistics
        System.out.println("\n--- Load Balancing Statistics ---");
        loadBalancing.getUsageStatistics().forEach((id, count) ->
                System.out.println("Elevator " + id + ": " + count + " requests"));

        // Demonstrate maintenance mode
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TESTING MAINTENANCE MODE");
        System.out.println("=".repeat(60));
        elevator2.setMaintenanceMode(true);
        controller.requestElevator(4, Direction.UP);

        // Show system status
        controller.printSystemStatus();

        // Show monitoring logs
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RECENT MONITORING LOGS");
        System.out.println("=".repeat(60));
        for (String log : monitor.getRecentLogs(10)) {
            System.out.println(log);
        }

        // Show alert statistics
        System.out.println("\n--- Alert Statistics ---");
        System.out.println("Maintenance alerts: " + alerts.getMaintenanceAlertCount());

        // Summary
        printSolidPrinciplesSummary();
    }

    private static void printSolidPrinciplesSummary() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SOLID PRINCIPLES DEMONSTRATED:");
        System.out.println("=".repeat(60));
        System.out.println("✓ SRP: Each class has single, well-defined responsibility");
        System.out.println("  - Elevator: Manages state and movement");
        System.out.println("  - ElevatorController: Coordinates elevators");
        System.out.println("  - Strategies: Implement selection algorithms");
        System.out.println("  - Observers: Handle notifications");
        System.out.println();
        System.out.println("✓ OCP: Open for extension, closed for modification");
        System.out.println("  - New strategies added without changing existing code");
        System.out.println("  - NearestElevatorStrategy, OptimalDirectionStrategy, LoadBalancingStrategy");
        System.out.println();
        System.out.println("✓ LSP: Implementations are substitutable");
        System.out.println("  - All observers (DisplayPanel, MonitoringSystem, AlertSystem)");
        System.out.println("  - All strategies can be swapped seamlessly");
        System.out.println();
        System.out.println("✓ ISP: Small, focused interfaces");
        System.out.println("  - IElevatorStrategy: Selection logic only");
        System.out.println("  - IRequestHandler: Request handling only");
        System.out.println("  - IElevatorObserver: Observation only");
        System.out.println();
        System.out.println("✓ DIP: Dependencies on abstractions");
        System.out.println("  - Controller depends on IRequestHandler");
        System.out.println("  - RequestHandler depends on IElevatorStrategy");
        System.out.println("  - Elevator depends on IElevatorObserver");
        System.out.println("=".repeat(60));
    }
}
