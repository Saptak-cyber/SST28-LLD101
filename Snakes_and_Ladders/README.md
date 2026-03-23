# Snakes & Ladders Game

A Java implementation of the classic Snakes & Ladders board game following SOLID principles.

## Features
- Configurable board size (n x n)
- Multiple players support
- Difficulty levels (easy/hard)
- Random snake and ladder placement
- Turn-based gameplay
- Automatic win detection

## Class Diagram

```
┌─────────────────┐
│   GameEngine    │
├─────────────────┤
│ - board         │
│ - players       │
│ - dice          │
│ - currentPlayer │
├─────────────────┤
│ + start()       │
│ + playTurn()    │
└─────────────────┘
        │
        ├──────────────────┬──────────────────┬──────────────────┐
        │                  │                  │                  │
┌───────▼──────┐  ┌────────▼────────┐  ┌─────▼──────┐  ┌───────▼──────┐
│    Board     │  │     Player      │  │    Dice    │  │ DiceStrategy │
├──────────────┤  ├─────────────────┤  ├────────────┤  ├──────────────┤
│ - size       │  │ - name          │  │ - strategy │  │ <<interface>>│
│ - snakes     │  │ - position      │  ├────────────┤  ├──────────────┤
│ - ladders    │  │ - isActive      │  │ + roll()   │  │ + roll()     │
├──────────────┤  ├─────────────────┤  └────────────┘  └──────────────┘
│ + move()     │  │ + move()        │         │                │
│ + hasWon()   │  │ + getPosition() │         │                │
└──────────────┘  └─────────────────┘         │                │
        │                                      │                │
        ├──────────────┬──────────────┐       │         ┌──────┴──────┐
        │              │              │       │         │             │
┌───────▼──────┐ ┌────▼──────┐ ┌────▼──────┐ │  ┌──────▼──────┐ ┌───▼────────┐
│    Snake     │ │   Ladder  │ │  Position │ │  │  EasyDice   │ │  HardDice  │
├──────────────┤ ├───────────┤ ├───────────┤ │  ├─────────────┤ ├────────────┤
│ - head       │ │ - start   │ │ - value   │ │  │ + roll()    │ │ + roll()   │
│ - tail       │ │ - end     │ └───────────┘ │  └─────────────┘ └────────────┘
├──────────────┤ ├───────────┤               │
│ + getHead()  │ │ + getStart│               │
│ + getTail()  │ │ + getEnd()│               │
└──────────────┘ └───────────┘               │
                                              │
                                    ┌─────────▼─────────┐
                                    │ BoardInitializer  │
                                    ├───────────────────┤
                                    │ + placeSnakes()   │
                                    │ + placeLadders()  │
                                    └───────────────────┘
```

## SOLID Principles Applied

1. **Single Responsibility Principle (SRP)**
   - Each class has one reason to change
   - `Board`: Manages board state
   - `Player`: Manages player state
   - `Dice`: Handles dice rolling
   - `GameEngine`: Orchestrates game flow

2. **Open/Closed Principle (OCP)**
   - `DiceStrategy` interface allows extending dice behavior without modifying existing code
   - Easy to add new difficulty levels

3. **Liskov Substitution Principle (LSP)**
   - `EasyDice` and `HardDice` can substitute `DiceStrategy` without breaking functionality

4. **Interface Segregation Principle (ISP)**
   - Small, focused interfaces
   - Classes implement only what they need

5. **Dependency Inversion Principle (DIP)**
   - `Dice` depends on `DiceStrategy` abstraction, not concrete implementations
   - High-level modules don't depend on low-level modules

## Running the Game

```bash
javac *.java
java Main
```

Follow the prompts to configure and play the game.
