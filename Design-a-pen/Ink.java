// Dependency Inversion Principle - depend on abstraction
public interface Ink {
    String getColor();
    int getQuantity();
    void consume(int amount);
    boolean isEmpty();
}
