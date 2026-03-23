/**
 * Strategy interface for dice rolling
 * OCP: Open for extension (new strategies), closed for modification
 * DIP: High-level modules depend on this abstraction
 */
public interface DiceStrategy {
    int roll();
}
