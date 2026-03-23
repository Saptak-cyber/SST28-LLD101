# Multilevel Parking Lot System - Design Document

## Class Diagram

```
┌─────────────────┐
│   ParkingLot    │ (Facade)
├─────────────────┤
│ - slotManager   │
│ - billingService│
│ - ticketService │
├─────────────────┤
│ + park()        │
│ + status()      │
│ + exit()        │
└────────┬────────┘
         │
         │ uses
         ├──────────────┬──────────────┬──────────────┐
         │              │              │              │
┌────────▼────────┐ ┌──▼──────────┐ ┌─▼─────────┐ ┌──▼──────────┐
│  SlotManager    │ │BillingService│ │TicketSvc  │ │ParkingSlot  │
├─────────────────┤ ├──────────────┤ ├───────────┤ ├─────────────┤
│ - slots         │ │ - hourlyRates│ │ - tickets │ │ - slotId    │
│ - strategy      │ └──────────────┘ └───────────┘ │ - type      │
├─────────────────┤                                │ - gateId    │
│ + findSlot()    │                                │ - isOccupied│
│ + getStatus()   │                                ├─────────────┤
└─────────────────┘                                │ + occupy()  │
         │                                         │ + vacate()  │
         │ uses                                    └─────────────┘
         ▼
┌──────────────────────┐
│SlotAllocationStrategy│ (Interface)
├──────────────────────┤
│ + findSlot()         │
└──────────────────────┘
         △
         │ implements
         │
┌────────┴──────────────────┐
│NearestSlotAllocationStrategy│
└───────────────────────────┘

┌──────────┐      ┌──────────────┐      ┌──────────┐
│ Vehicle  │      │ParkingTicket │      │ SlotType │
├──────────┤      ├──────────────┤      ├──────────┤
│ - regNo  │      │ - ticketId   │      │ SMALL    │
│ - type   │      │ - vehicle    │      │ MEDIUM   │
└──────────┘      │ - slotNumber │      │ LARGE    │
                  │ - slotType   │      └──────────┘
                  │ - entryTime  │
                  └──────────────┘
```

## SOLID Principles Applied

### 1. Single Responsibility Principle (SRP)
Each class has one clear responsibility:
- `ParkingLot`: Facade coordinating the system
- `SlotManager`: Manages slot allocation and availability
- `BillingService`: Handles billing calculations
- `TicketService`: Manages ticket lifecycle
- `ParkingSlot`: Represents a single parking slot

### 2. Open/Closed Principle (OCP)
- `SlotAllocationStrategy` interface allows new allocation strategies without modifying existing code
- Can add new strategies like `RandomAllocationStrategy` or `PriorityAllocationStrategy`

### 3. Liskov Substitution Principle (LSP)
- Any implementation of `SlotAllocationStrategy` can replace another without breaking functionality
- `NearestSlotAllocationStrategy` can be swapped with other strategies seamlessly

### 4. Interface Segregation Principle (ISP)
- Small, focused interfaces like `SlotAllocationStrategy` with single method
- Classes only depend on methods they actually use

### 5. Dependency Inversion Principle (DIP)
- `SlotManager` depends on `SlotAllocationStrategy` interface, not concrete implementation
- High-level `ParkingLot` depends on abstractions (`SlotManager`, `BillingService`)

## Key Design Decisions

### Slot Allocation
- Uses Strategy pattern for flexible allocation algorithms
- Nearest slot prioritizes same gate, then any available slot
- Compatible slot types determined by vehicle type

### Billing
- Charges based on allocated slot type, not vehicle type
- Rounds up to nearest hour
- Rates configurable per slot type

### Ticket Management
- UUID-based unique ticket IDs
- Stores all necessary information for billing
- Active tickets tracked for validation

## How to Run
```bash
javac *.java
java Demo
```
