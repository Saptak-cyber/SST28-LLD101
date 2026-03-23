# Pen System - SOLID Principles Implementation

A comprehensive Java implementation of a Pen system demonstrating all five SOLID principles.

## Structure

- `Pen.java` - Main pen interface
- `Openable.java` - Interface for open/close operations
- `Refillable.java` - Interface for refill operations
- `Ink.java` - Ink abstraction
- `StandardInk.java` - Concrete ink implementation
- `WritingStrategy.java` - Strategy for writing behavior
- `StandardWritingStrategy.java` - Default writing implementation
- `AbstractPen.java` - Base pen class with common functionality
- `BallpointPen.java` - Ballpoint pen implementation
- `FountainPen.java` - Fountain pen implementation
- `PenState.java` - Enum for pen states
- `PenDemo.java` - Demo application
- `CLASS_DIAGRAM.md` - Visual class diagram and SOLID principles explanation

## How to Run

```bash
javac *.java
java PenDemo
```

## Features

- Open/close pen functionality
- Write with different colored inks
- Refill pen with new ink
- State management (open/closed)
- Ink level tracking
- Multiple pen types (Ballpoint, Fountain)
- Extensible design for new pen types and behaviors

## SOLID Principles

All five SOLID principles are implemented:
- **S**ingle Responsibility
- **O**pen/Closed
- **L**iskov Substitution
- **I**nterface Segregation
- **D**ependency Inversion

See `CLASS_DIAGRAM.md` for detailed explanation.
