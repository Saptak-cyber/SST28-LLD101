import java.util.*;

/**
 * Represents the game board with snakes and ladders
 * SRP: Single responsibility of managing board state and movements
 */
public class Board {
    private final int size;
    private final int maxPosition;
    private final Map<Integer, Snake> snakes;
    private final Map<Integer, Ladder> ladders;

    public Board(int size, List<Snake> snakeList, List<Ladder> ladderList) {
        this.size = size;
        this.maxPosition = size * size;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
        
        for (Snake snake : snakeList) {
            snakes.put(snake.getHead().getValue(), snake);
        }
        
        for (Ladder ladder : ladderList) {
            ladders.put(ladder.getStart().getValue(), ladder);
        }
    }

    public int getMaxPosition() {
        return maxPosition;
    }

    public Position getNewPosition(Position currentPosition, int diceValue) {
        int newPos = currentPosition.getValue() + diceValue;
        
        // Don't move if going beyond board
        if (newPos > maxPosition) {
            return currentPosition;
        }
        
        Position finalPosition = new Position(newPos);
        
        // Check for snake
        if (snakes.containsKey(newPos)) {
            finalPosition = snakes.get(newPos).getTail();
            System.out.println("  Oops! Snake bite at " + newPos + "! Sliding down to " + finalPosition.getValue());
        }
        
        // Check for ladder
        if (ladders.containsKey(newPos)) {
            finalPosition = ladders.get(newPos).getEnd();
            System.out.println("  Yay! Ladder at " + newPos + "! Climbing up to " + finalPosition.getValue());
        }
        
        return finalPosition;
    }

    public boolean hasWon(Position position) {
        return position.getValue() == maxPosition;
    }

    public void displayBoard() {
        System.out.println("\n=== Board Configuration ===");
        System.out.println("Board Size: " + size + "x" + size + " (1-" + maxPosition + ")");
        System.out.println("\nSnakes:");
        snakes.values().forEach(System.out::println);
        System.out.println("\nLadders:");
        ladders.values().forEach(System.out::println);
        System.out.println("===========================\n");
    }
}
