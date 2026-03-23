# Pen System - Class Diagram

```
┌─────────────────────┐
│   <<interface>>     │
│        Pen          │
├─────────────────────┤
│ + write(text)       │
└─────────────────────┘
          △
          │
          │
┌─────────────────────┐         ┌─────────────────────┐
│   <<interface>>     │         │   <<interface>>     │
│     Openable        │         │    Refillable       │
├─────────────────────┤         ├─────────────────────┤
│ + start()           │         │ + refill(Ink)       │
│ + close()           │         └─────────────────────┘
└─────────────────────┘                   △
          △                               │
          │                               │
          └───────────┬───────────────────┘
                      │
              ┌───────────────┐
              │  AbstractPen  │◇────────────┐
              ├───────────────┤             │
              │ # ink         │             │
              │ # state       │             │
              │ # strategy    │             │
              ├───────────────┤             │
              │ + start()     │             │
              │ + close()     │             │
              │ + write()     │             │
              │ + refill()    │             │
              │ + getState()  │             │
              └───────────────┘             │
                      △                     │
                      │                     │
          ┌───────────┴───────────┐         │
          │                       │         │
  ┌───────────────┐       ┌───────────────┐ │
  │ BallpointPen  │       │  FountainPen  │ │
  └───────────────┘       └───────────────┘ │
                                            │
                                            │
┌─────────────────────┐                     │
│   <<interface>>     │                     │
│        Ink          │◇────────────────────┘
├─────────────────────┤
│ + getColor()        │
│ + getQuantity()     │
│ + consume(amount)   │
│ + isEmpty()         │
└─────────────────────┘
          △
          │
  ┌───────────────┐
  │  StandardInk  │
  ├───────────────┤
  │ - color       │
  │ - quantity    │
  └───────────────┘


┌─────────────────────────┐
│     <<interface>>       │
│   WritingStrategy       │
├─────────────────────────┤
│ + performWrite(text,ink)│
└─────────────────────────┘
          △
          │
┌─────────────────────────┐
│StandardWritingStrategy  │
└─────────────────────────┘


┌─────────────────┐
│  <<enumeration>>│
│    PenState     │
├─────────────────┤
│ CLOSED          │
│ OPEN            │
└─────────────────┘
```

## SOLID Principles Applied

### 1. Single Responsibility Principle (SRP)
- **Pen interface**: Only responsible for writing
- **Openable interface**: Only responsible for open/close operations
- **Refillable interface**: Only responsible for refilling
- **Ink interface**: Only responsible for ink management
- **WritingStrategy**: Only responsible for writing logic
- Each class has one reason to change

### 2. Open/Closed Principle (OCP)
- **WritingStrategy interface**: New writing behaviors can be added without modifying existing code
- **AbstractPen**: Can be extended with new pen types without modification
- **Ink interface**: New ink types can be added without changing pen implementation

### 3. Liskov Substitution Principle (LSP)
- **BallpointPen** and **FountainPen** can substitute **AbstractPen** without breaking functionality
- All subclasses maintain the contract of their parent class
- Any code using AbstractPen works correctly with any subclass

### 4. Interface Segregation Principle (ISP)
- Separated into small, focused interfaces: **Pen**, **Openable**, **Refillable**
- Clients only depend on interfaces they actually use
- No client is forced to implement methods it doesn't need

### 5. Dependency Inversion Principle (DIP)
- **AbstractPen** depends on **Ink** abstraction, not concrete implementation
- **AbstractPen** depends on **WritingStrategy** abstraction
- High-level modules don't depend on low-level modules; both depend on abstractions

## Key Design Features

1. **Composition over Inheritance**: Pen uses Ink and WritingStrategy through composition
2. **Strategy Pattern**: WritingStrategy allows different writing behaviors
3. **State Pattern**: PenState enum manages pen state
4. **Template Method**: AbstractPen provides template for pen behavior
5. **Dependency Injection**: Ink and WritingStrategy are injected into pen

## Extensibility Examples

- Add new pen types: Extend AbstractPen
- Add new ink types: Implement Ink interface
- Add new writing behaviors: Implement WritingStrategy
- Add new capabilities: Create new interfaces (e.g., Erasable)
