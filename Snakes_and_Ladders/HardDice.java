import java.util.Random;

/**
 * Hard difficulty dice - weighted towards lower numbers
 * LSP: Can substitute DiceStrategy without breaking behavior
 */
public class HardDice implements DiceStrategy {
    private final Random random;

    public HardDice() {
        this.random = new Random();
    }

    @Override
    public int roll() {
        // Weighted roll: 40% chance of 1-2, 30% chance of 3-4, 30% chance of 5-6
        double rand = random.nextDouble();
        if (rand < 0.4) {
            return random.nextInt(2) + 1; // 1-2
        } else if (rand < 0.7) {
            return random.nextInt(2) + 3; // 3-4
        } else {
            return random.nextInt(2) + 5; // 5-6
        }
    }
}
