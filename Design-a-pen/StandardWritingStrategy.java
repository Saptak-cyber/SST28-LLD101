// Concrete writing strategy
public class StandardWritingStrategy implements WritingStrategy {
    @Override
    public void performWrite(String text, Ink ink) {
        if (text == null || text.isEmpty()) {
            return;
        }
        
        int inkNeeded = text.length();
        if (ink.getQuantity() >= inkNeeded) {
            System.out.println("Writing in " + ink.getColor() + ": " + text);
            ink.consume(inkNeeded);
        } else {
            System.out.println("Insufficient ink to write!");
        }
    }
}
