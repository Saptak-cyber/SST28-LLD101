# Elevator System Design (Java Implementation)

## Class Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                      <<interface>>                          │
│                   IElevatorStrategy                         │
├─────────────────────────────────────────────────────────────┤
│ + selectElevator(elevators, requestedFloor): Elevator      │
└─────────────────────────────────────────────────────────────┘
                            △
                            │ implements
          ┌─────────────────┼─────────────────┐
          │                 │                 │
┌─────────────────────┐ ┌──────────────────────┐ ┌─────────────────────┐
│NearestElevator      │ │OptimalDirection      │ │LoadBalancing        │
│Strategy             │ │Strategy              │ │Strategy             │
├─────────────────────┤ ├──────────────────────┤ ├─────────────────────┤
│+ selectElevator()   │ │+ selectElevator()    │ │+ selectElevator()   │
└─────────────────────┘ └──────────────────────┘ │+ getUsageStats()    │
                                                  └─────────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                      <<interface>>                          │
│                    IRequestHandler                          │
├─────────────────────────────────────────────────────────────┤
│ + handleRequest(elevators, floor, direction): Elevator     │
└─────────────────────────────────────────────────────────────┘
                            △
                            │ implements
                            │
┌─────────────────────────────────────────────────────────────┐
│              ElevatorRequestHandler                         │
├─────────────────────────────────────────────────────────────┤
│ - strategy: IElevatorStrategy                               │
├─────────────────────────────────────────────────────────────┤
│ + handleRequest(elevators, floor, direction): Elevator     │
│ + setStrategy(strategy): void                               │
└─────────────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                      <<interface>>                          │
│                   IElevatorObserver                         │
├─────────────────────────────────────────────────────────────┤
│ + update(elevatorId, floor, direction, state): void        │
└─────────────────────────────────────────────────────────────┘
                            △
                            │ implements
          ┌─────────────────┼─────────────────┐
          │                 │                 │
┌─────────────────┐ ┌──────────────────┐ ┌─────────────────┐
│DisplayPanel     │ │MonitoringSystem  │ │AlertSystem      │
├─────────────────┤ ├──────────────────┤ ├─────────────────┤
│+ update()       │ │- logs: List      │ │- alertCount     │
└─────────────────┘ │+ update()        │ │+ update()       │
                    │+ getLogs()       │ └─────────────────┘
                    └──────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                         Elevator                            │
├─────────────────────────────────────────────────────────────┤
│ - id: int                                                   │
│ - currentFloor: int                                         │
│ - direction: Direction                                      │
│ - state: ElevatorState                                      │
│ - totalFloors: int                                          │
│ - observers: List<IElevatorObserver>                        │
├─────────────────────────────────────────────────────────────┤
│ + moveToFloor(targetFloor): void                            │
│ + addObserver(observer): void                               │
│ + removeObserver(observer): void                            │
│ - notifyObservers(): void                                   │
│ + setMaintenanceMode(maintenance): void                     │
│ + getId(): int                                              │
│ + getCurrentFloor(): int                                    │
│ + getDirection(): Direction                                 │
│ + getState(): ElevatorState                                 │
└─────────────────────────────────────────────────────────────┘
                            △
                            │ uses
                            │
┌─────────────────────────────────────────────────────────────┐
│                   ElevatorController                        │
├─────────────────────────────────────────────────────────────┤
│ - elevators: List<Elevator>                                 │
│ - requestHandler: IRequestHandler                           │
├─────────────────────────────────────────────────────────────┤
│ + addElevator(elevator): void                               │
│ + requestElevator(floor, direction): void                   │
│ + getElevators(): List<Elevator>                            │
│ + getElevatorById(id): Elevator                             │
│ + printSystemStatus(): void                                 │
└─────────────────────────────────────────────────────────────┘


┌──────────────┐          ┌──────────────────┐
│  Direction   │          │  ElevatorState   │
├──────────────┤          ├──────────────────┤
│ UP           │          │ MOVING           │
│ DOWN         │          │ STOPPED          │
│ IDLE         │          │ MAINTENANCE      │
└──────────────┘          └──────────────────┘
```

## SOLID Principles Applied

### 1. Single Responsibility Principle (SRP)
- **Elevator**: Manages only elevator state and movement
- **ElevatorController**: Coordinates multiple elevators
- **ElevatorRequestHandler**: Handles request processing logic
- **DisplayPanel**: Handles display updates only
- **NearestElevatorStrategy**: Implements selection algorithm only

### 2. Open/Closed Principle (OCP)
- **IElevatorStrategy**: Open for extension (new strategies) but closed for modification
- New selection strategies can be added without changing existing code
- Example: Can add `OptimalLoadStrategy`, `RoundRobinStrategy` without modifying core

### 3. Liskov Substitution Principle (LSP)
- Any implementation of `IElevatorStrategy` can replace another without breaking functionality
- Any implementation of `IElevatorObserver` can be substituted
- Derived classes maintain the contract of their interfaces

### 4. Interface Segregation Principle (ISP)
- **IElevatorStrategy**: Focused interface for selection logic
- **IRequestHandler**: Focused interface for request handling
- **IElevatorObserver**: Focused interface for observation
- No client is forced to depend on methods it doesn't use

### 5. Dependency Inversion Principle (DIP)
- **ElevatorController** depends on `IRequestHandler` abstraction, not concrete implementation
- **ElevatorRequestHandler** depends on `IElevatorStrategy` abstraction
- **Elevator** depends on `IElevatorObserver` abstraction
- High-level modules don't depend on low-level modules; both depend on abstractions

## Key Design Patterns

1. **Strategy Pattern**: For elevator selection algorithms
2. **Observer Pattern**: For notifying displays and monitoring systems
3. **Dependency Injection**: For loose coupling between components

## Package Structure

```
com.elevator
├── Main.java                          # Entry point with demonstrations
├── enums/
│   ├── Direction.java                 # UP, DOWN, IDLE
│   └── ElevatorState.java             # MOVING, STOPPED, MAINTENANCE
├── interfaces/
│   ├── IElevatorObserver.java         # Observer interface (ISP)
│   ├── IElevatorStrategy.java         # Strategy interface (ISP, OCP)
│   └── IRequestHandler.java           # Request handler interface (ISP)
├── models/
│   └── Elevator.java                  # Core elevator model (SRP, DIP)
├── controllers/
│   └── ElevatorController.java        # System coordinator (SRP, DIP)
├── handlers/
│   └── ElevatorRequestHandler.java    # Request processor (SRP, DIP, OCP)
├── strategies/
│   ├── NearestElevatorStrategy.java   # Nearest selection (OCP, LSP)
│   ├── OptimalDirectionStrategy.java  # Direction-based selection (OCP, LSP)
│   └── LoadBalancingStrategy.java     # Load balancing (OCP, LSP)
└── observers/
    ├── DisplayPanel.java              # Display observer (SRP, LSP, ISP)
    ├── MonitoringSystem.java          # Logging observer (SRP, LSP, ISP)
    └── AlertSystem.java               # Alert observer (SRP, LSP, ISP)
```

## System Flow

1. User requests elevator from a floor
2. ElevatorController receives request
3. RequestHandler uses Strategy to select best elevator
4. Selected Elevator moves to requested floor
5. Observers are notified of state changes
6. DisplayPanel, MonitoringSystem, and AlertSystem update
