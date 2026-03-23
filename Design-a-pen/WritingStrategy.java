// Open/Closed Principle - strategy for different writing behaviors
public interface WritingStrategy {
    void performWrite(String text, Ink ink);
}
