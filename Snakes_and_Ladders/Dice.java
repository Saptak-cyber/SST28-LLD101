/**
 * Dice class that uses a strategy for rolling
 * DIP: Depends on DiceStrategy abstraction, not concrete implementations
 */
public class Dice {
    private final DiceStrategy strategy;

    public Dice(DiceStrategy strategy) {
        this.strategy = strategy;
    }

    public int roll() {
        return strategy.roll();
    }
}
