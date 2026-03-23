import java.util.*;

/**
 * Responsible for initializing board with snakes and ladders
 * SRP: Single responsibility of board setup
 */
public class BoardInitializer {
    private final Random random;

    public BoardInitializer() {
        this.random = new Random();
    }

    public Board createBoard(int size, int numSnakes, int numLadders) {
        int maxPosition = size * size;
        Set<Integer> occupiedPositions = new HashSet<>();
        
        // Reserve first and last positions
        occupiedPositions.add(1);
        occupiedPositions.add(maxPosition);
        
        List<Snake> snakes = generateSnakes(numSnakes, maxPosition, occupiedPositions);
        List<Ladder> ladders = generateLadders(numLadders, maxPosition, occupiedPositions);
        
        return new Board(size, snakes, ladders);
    }

    private List<Snake> generateSnakes(int count, int maxPosition, Set<Integer> occupied) {
        List<Snake> snakes = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            int head = getRandomPosition(maxPosition / 2, maxPosition - 1, occupied);
            int tail = getRandomPosition(2, head - 1, occupied);
            
            occupied.add(head);
            occupied.add(tail);
            
            snakes.add(new Snake(new Position(head), new Position(tail)));
        }
        
        return snakes;
    }

    private List<Ladder> generateLadders(int count, int maxPosition, Set<Integer> occupied) {
        List<Ladder> ladders = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            int start = getRandomPosition(2, maxPosition / 2, occupied);
            int end = getRandomPosition(start + 1, maxPosition - 1, occupied);
            
            occupied.add(start);
            occupied.add(end);
            
            ladders.add(new Ladder(new Position(start), new Position(end)));
        }
        
        return ladders;
    }

    private int getRandomPosition(int min, int max, Set<Integer> occupied) {
        int attempts = 0;
        int maxAttempts = 1000;
        
        while (attempts < maxAttempts) {
            int pos = random.nextInt(max - min + 1) + min;
            if (!occupied.contains(pos)) {
                return pos;
            }
            attempts++;
        }
        
        // Fallback: find first available position
        for (int i = min; i <= max; i++) {
            if (!occupied.contains(i)) {
                return i;
            }
        }
        
        throw new IllegalStateException("Could not find available position on board");
    }
}
