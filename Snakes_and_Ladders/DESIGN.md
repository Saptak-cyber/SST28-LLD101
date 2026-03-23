# Snakes & Ladders - Design Document

## Overview
This document describes the design and architecture of the Snakes & Ladders game implementation in Java, following SOLID principles.

## Class Diagram

```
                                    ┌─────────────────────┐
                                    │       Main          │
                                    │  (Entry Point)      │
                                    └──────────┬──────────┘
                                               │
                                               │ creates
                                               ▼
                                    ┌─────────────────────┐
                                    │    GameEngine       │
                                    ├─────────────────────┤
                                    │ - board: Board      │
                                    │ - players: List     │
                                    │ - dice: Dice        │
                                    │ - currentPlayerIdx  │
                                    ├─────────────────────┤
                                    │ + start(): void     │
                                    │ + playTurn(): void  │
                                    │ - shouldContinue()  │
                                    │ - handleWinner()    │
                                    └──┬────────┬─────┬───┘
                                       │        │     │
                        ┌──────────────┘        │     └──────────────┐
                        │                       │                    │
                        ▼                       ▼                    ▼
            ┌───────────────────┐   ┌──────────────────┐   ┌────────────────┐
            │      Board        │   │     Player       │   │     Dice       │
            ├───────────────────┤   ├──────────────────┤   ├────────────────┤
            │ - size: int       │   │ - name: String   │   │ - strategy     │
            │ - maxPosition     │   │ - position: Pos  │   │                │
            │ - snakes: Map     │   │ - isActive: bool │   ├────────────────┤
            │ - ladders: Map    │   ├──────────────────┤   │ + roll(): int  │
            ├───────────────────┤   │ + move(int)      │   └────────┬───────┘
            │ + getNewPosition()│   │ + getPosition()  │            │
            │ + hasWon()        │   │ + setActive()    │            │ uses
            │ + displayBoard()  │   └──────────────────┘            │
            └─────┬─────────────┘                                   ▼
                  │ contains                          ┌──────────────────────┐
                  │                                   │   DiceStrategy       │
        ┌─────────┴─────────┐                        │   <<interface>>      │
        │                   │                        ├──────────────────────┤
        ▼                   ▼                        │ + roll(): int        │
┌───────────────┐   ┌───────────────┐              └──────┬───────────┬────┘
│     Snake     │   │    Ladder     │                     │           │
├───────────────┤   ├───────────────┤                     │           │
│ - head: Pos   │   │ - start: Pos  │              ┌──────▼──────┐ ┌──▼─────────┐
│ - tail: Pos   │   │ - end: Pos    │              │  EasyDice   │ │  HardDice  │
├───────────────┤   ├───────────────┤              ├─────────────┤ ├────────────┤
│ + getHead()   │   │ + getStart()  │              │ + roll()    │ │ + roll()   │
│ + getTail()   │   │ + getEnd()    │              └─────────────┘ └────────────┘
└───────┬───────┘   └───────┬───────┘
        │                   │
        │ uses              │ uses
        ▼                   ▼
    ┌───────────────────────────┐
    │       Position            │
    ├───────────────────────────┤
    │ - value: int              │
    ├───────────────────────────┤
    │ + getValue(): int         │
    │ + equals(Object): boolean │
    └───────────────────────────┘

            ┌──────────────────────┐
            │  BoardInitializer    │
            ├──────────────────────┤
            │ - random: Random     │
            ├──────────────────────┤
            │ + createBoard()      │
            │ - generateSnakes()   │
            │ - generateLadders()  │
            └──────────────────────┘
```

## SOLID Principles Implementation

### 1. Single Responsibility Principle (SRP)
Each class has one clear responsibility:

- **Position**: Manages position value and equality
- **Snake**: Represents snake properties (head, tail)
- **Ladder**: Represents ladder properties (start, end)
- **Player**: Manages player state (name, position, active status)
- **Board**: Manages board state and position calculations
- **Dice**: Handles dice rolling using a strategy
- **BoardInitializer**: Responsible for board setup
- **GameEngine**: Orchestrates game flow and rules
- **Main**: Entry point and user input handling

### 2. Open/Closed Principle (OCP)
The system is open for extension but closed for modification:

- **DiceStrategy Interface**: New difficulty levels can be added by implementing the interface without modifying existing code
- **Example**: Adding a "Medium" difficulty:
  ```java
  public class MediumDice implements DiceStrategy {
      public int roll() {
          // Custom implementation
      }
  }
  ```

### 3. Liskov Substitution Principle (LSP)
Subtypes can replace their base types:

- **EasyDice** and **HardDice** both implement **DiceStrategy**
- Either can be substituted without breaking the Dice class functionality
- The Dice class works correctly with any DiceStrategy implementation

### 4. Interface Segregation Principle (ISP)
Interfaces are small and focused:

- **DiceStrategy**: Single method interface `roll()`
- Classes only implement what they need
- No client is forced to depend on methods it doesn't use

### 5. Dependency Inversion Principle (DIP)
High-level modules depend on abstractions:

- **Dice** depends on **DiceStrategy** (abstraction), not concrete implementations
- **GameEngine** depends on **Board**, **Player**, and **Dice** abstractions
- Concrete implementations (EasyDice, HardDice) depend on the DiceStrategy abstraction

## Design Patterns Used

### 1. Strategy Pattern
- **Context**: Dice
- **Strategy Interface**: DiceStrategy
- **Concrete Strategies**: EasyDice, HardDice
- **Purpose**: Allows runtime selection of dice rolling algorithm based on difficulty

### 2. Factory Pattern (Implicit)
- **BoardInitializer** acts as a factory for creating configured Board instances
- Encapsulates the complex logic of placing snakes and ladders

### 3. Value Object Pattern
- **Position** is an immutable value object
- Provides equality comparison based on value
- Used throughout the system to represent board positions

## Key Design Decisions

### 1. Immutable Position
Position is immutable to prevent accidental modifications and ensure thread safety.

### 2. Map-based Snake/Ladder Storage
Board uses Maps for O(1) lookup of snakes and ladders by position, improving performance.

### 3. Strategy-based Dice
Allows easy extension of difficulty levels without modifying core game logic.

### 4. Separation of Concerns
- Board handles position logic
- GameEngine handles game flow
- BoardInitializer handles setup
- Each class has minimal dependencies

### 5. Validation in Constructors
Snake and Ladder constructors validate that positions are logically correct (head > tail, start < end).

## Game Rules Implementation

1. **Board Movement**: Handled by `Board.getNewPosition()`
2. **Snake/Ladder Logic**: Checked after each move in `Board.getNewPosition()`
3. **Win Condition**: Checked by `Board.hasWon()`
4. **Boundary Check**: Prevents moving beyond board size
5. **Turn Management**: Handled by `GameEngine.moveToNextPlayer()`
6. **Game End**: When only 0-1 active players remain

## Extensibility

The design allows easy extension:

1. **New Difficulty Levels**: Implement DiceStrategy
2. **Different Board Shapes**: Extend Board class
3. **Special Tiles**: Add new tile types similar to Snake/Ladder
4. **Multiple Dice**: Modify Dice to use multiple strategies
5. **AI Players**: Extend Player class with AI logic
6. **Save/Load Game**: Add serialization to game state classes

## Testing Considerations

Each class can be tested independently:

- **Unit Tests**: Test each class in isolation
- **Integration Tests**: Test GameEngine with mock dependencies
- **Strategy Tests**: Test each DiceStrategy implementation
- **Board Tests**: Test snake/ladder placement and movement logic

## Conclusion

This design follows SOLID principles to create a maintainable, extensible, and testable implementation of Snakes & Ladders. The use of interfaces and separation of concerns makes it easy to add new features without modifying existing code.
