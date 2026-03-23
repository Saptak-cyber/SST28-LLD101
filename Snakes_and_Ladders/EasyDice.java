import java.util.Random;

/**
 * Easy difficulty dice - standard 1-6 roll
 * LSP: Can substitute DiceStrategy without breaking behavior
 */
public class EasyDice implements DiceStrategy {
    private final Random random;

    public EasyDice() {
        this.random = new Random();
    }

    @Override
    public int roll() {
        return random.nextInt(6) + 1;
    }
}
