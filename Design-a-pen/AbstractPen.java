// Single Responsibility Principle - base pen with common functionality
// Liskov Substitution Principle - can be substituted by any subclass
public abstract class AbstractPen implements Pen, Openable, Refillable {
    protected Ink ink;
    protected PenState state;
    protected WritingStrategy writingStrategy;

    public AbstractPen(Ink ink, WritingStrategy writingStrategy) {
        this.ink = ink;
        this.state = PenState.CLOSED;
        this.writingStrategy = writingStrategy;
    }

    @Override
    public void start() {
        if (state == PenState.CLOSED) {
            state = PenState.OPEN;
            System.out.println("Pen opened");
        } else {
            System.out.println("Pen is already open");
        }
    }

    @Override
    public void close() {
        if (state == PenState.OPEN) {
            state = PenState.CLOSED;
            System.out.println("Pen closed");
        } else {
            System.out.println("Pen is already closed");
        }
    }

    @Override
    public void write(String text) {
        if (state == PenState.CLOSED) {
            System.out.println("Cannot write. Pen is closed. Please open it first.");
            return;
        }
        
        if (ink.isEmpty()) {
            System.out.println("Cannot write. Pen is out of ink. Please refill.");
            return;
        }
        
        writingStrategy.performWrite(text, ink);
    }

    @Override
    public void refill(Ink newInk) {
        this.ink = newInk;
        System.out.println("Pen refilled with " + newInk.getColor() + " ink");
    }

    public PenState getState() {
        return state;
    }

    public int getInkLevel() {
        return ink.getQuantity();
    }
}
