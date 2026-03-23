// Concrete implementation of Ink
public class StandardInk implements Ink {
    private final String color;
    private int quantity;

    public StandardInk(String color, int quantity) {
        this.color = color;
        this.quantity = quantity;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void consume(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
        } else {
            quantity = 0;
        }
    }

    @Override
    public boolean isEmpty() {
        return quantity <= 0;
    }
}
