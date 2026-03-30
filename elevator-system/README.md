# Elevator System - SOLID Principles Implementation (Java)

A comprehensive elevator system demonstrating all five SOLID principles with clean architecture and design patterns.

## Project Structure

```
.
├── DESIGN.md                          # Detailed design documentation with class diagram
├── README.md                          # This file
├── pom.xml                            # Maven build configuration
├── build.gradle                       # Gradle build configuration
└── src/main/java/com/elevator/
    ├── Main.java                      # Entry point
    ├── enums/                         # Enumerations
    ├── interfaces/                    # Interface definitions (ISP)
    ├── models/                        # Domain models
    ├── controllers/                   # System controllers
    ├── handlers/                      # Request handlers
    ├── strategies/                    # Strategy implementations (OCP)
    └── observers/                     # Observer implementations (LSP)
```

## Running the System

### Using Maven:
```bash
mvn clean compile
mvn exec:java
```

### Using Gradle:
```bash
gradle build
gradle run
```

### Using Java directly:
```bash
javac -d bin src/main/java/com/elevator/**/*.java
java -cp bin com.elevator.Main
```

## SOLID Principles Implementation

### 1. Single Responsibility Principle (SRP)
Each class has exactly one reason to change:
- `Elevator` - Manages elevator state and movement only
- `ElevatorController` - Coordinates multiple elevators only
- `ElevatorRequestHandler` - Handles request processing only
- `DisplayPanel` - Handles display updates only
- `NearestElevatorStrategy` - Implements selection algorithm only

### 2. Open/Closed Principle (OCP)
The system is open for extension but closed for modification:
- New elevator selection strategies can be added by implementing `IElevatorStrategy`
- Examples: `NearestElevatorStrategy`, `OptimalDirectionStrategy`
- No need to modify existing code to add new strategies

### 3. Liskov Substitution Principle (LSP)
Subtypes are fully substitutable:
- Any `IElevatorObserver` implementation (DisplayPanel, MonitoringSystem) can replace another
- Any `IElevatorStrategy` implementation can be swapped without breaking functionality
- Derived classes maintain the contract of their base interfaces

### 4. Interface Segregation Principle (ISP)
Small, focused interfaces:
- `IElevatorStrategy` - Only selection logic
- `IRequestHandler` - Only request handling
- `IElevatorObserver` - Only observation updates
- No client depends on methods it doesn't use

### 5. Dependency Inversion Principle (DIP)
High-level modules depend on abstractions:
- `ElevatorController` depends on `IRequestHandler` interface
- `ElevatorRequestHandler` depends on `IElevatorStrategy` interface
- `Elevator` depends on `IElevatorObserver` interface
- Dependencies are injected, enabling loose coupling

## Design Patterns Used

1. **Strategy Pattern** - Pluggable elevator selection algorithms
2. **Observer Pattern** - Elevator state change notifications
3. **Dependency Injection** - Loose coupling between components

## Key Features

- Multiple elevator coordination
- Pluggable selection strategies
- Real-time state monitoring
- Observer notifications
- Maintenance mode support
- Type-safe enums for states and directions

## Example Output

```
=== Request: Floor 5, Direction UP ===
Selected Elevator 1 for floor 5
Elevator 1 at floor 2
[DISPLAY] Elevator 1: Floor 2, UP, MOVING
Elevator 1 at floor 3
[DISPLAY] Elevator 1: Floor 3, UP, MOVING
...
```

## Extending the System

### Add a New Selection Strategy

```java
public class CustomStrategy implements IElevatorStrategy {
    @Override
    public Elevator selectElevator(List<Elevator> elevators, int requestedFloor) {
        // Your custom logic here
        return selectedElevator;
    }
}

// Use it
IElevatorStrategy strategy = new CustomStrategy();
ElevatorRequestHandler handler = new ElevatorRequestHandler(strategy);
```

### Add a New Observer

```java
public class CustomObserver implements IElevatorObserver {
    @Override
    public void update(int elevatorId, int floor, Direction direction, ElevatorState state) {
        // Your custom notification logic
    }
}

// Attach it
elevator.addObserver(new CustomObserver());
```

## Benefits of This Design

- **Maintainable** - Changes are isolated to specific classes
- **Testable** - Each component can be tested independently
- **Extensible** - New features can be added without modifying existing code
- **Flexible** - Strategies and observers can be swapped at runtime
- **Decoupled** - Components depend on abstractions, not implementations
