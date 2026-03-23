/**
 * Represents a player in the game
 * SRP: Single responsibility of managing player state
 */
public class Player {
    private final String name;
    private Position position;
    private boolean isActive;

    public Player(String name) {
        this.name = name;
        this.position = new Position(0);
        this.isActive = true;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void move(int steps) {
        int newPosition = position.getValue() + steps;
        this.position = new Position(newPosition);
    }

    @Override
    public String toString() {
        return name + " (Position: " + position.getValue() + ")";
    }
}
