/**
 * Represents a ladder on the board
 * SRP: Single responsibility of managing ladder properties
 */
public class Ladder {
    private final Position start;
    private final Position end;

    public Ladder(Position start, Position end) {
        if (start.getValue() >= end.getValue()) {
            throw new IllegalArgumentException("Ladder start must be at a lower position than end");
        }
        this.start = start;
        this.end = end;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Ladder[" + start.getValue() + " -> " + end.getValue() + "]";
    }
}
