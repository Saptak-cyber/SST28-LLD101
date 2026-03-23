/**
 * Represents a snake on the board
 * SRP: Single responsibility of managing snake properties
 */
public class Snake {
    private final Position head;
    private final Position tail;

    public Snake(Position head, Position tail) {
        if (head.getValue() <= tail.getValue()) {
            throw new IllegalArgumentException("Snake head must be at a higher position than tail");
        }
        this.head = head;
        this.tail = tail;
    }

    public Position getHead() {
        return head;
    }

    public Position getTail() {
        return tail;
    }

    @Override
    public String toString() {
        return "Snake[" + head.getValue() + " -> " + tail.getValue() + "]";
    }
}
