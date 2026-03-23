import java.util.*;

/**
 * Main game engine that orchestrates the game flow
 * SRP: Single responsibility of managing game flow and rules
 */
public class GameEngine {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private int currentPlayerIndex;

    public GameEngine(Board board, List<Player> players, Dice dice) {
        this.board = board;
        this.players = players;
        this.dice = dice;
        this.currentPlayerIndex = 0;
    }

    public void start() {
        System.out.println("\n🎲 Game Started! 🎲\n");
        board.displayBoard();
        
        displayPlayers();
        
        Scanner scanner = new Scanner(System.in);
        
        while (shouldContinue()) {
            Player currentPlayer = players.get(currentPlayerIndex);
            
            if (!currentPlayer.isActive()) {
                moveToNextPlayer();
                continue;
            }
            
            System.out.println("\n" + currentPlayer.getName() + "'s turn (Position: " + 
                             currentPlayer.getPosition().getValue() + ")");
            System.out.print("Press Enter to roll the dice...");
            scanner.nextLine();
            
            playTurn(currentPlayer);
            
            if (board.hasWon(currentPlayer.getPosition())) {
                handleWinner(currentPlayer);
            }
            
            moveToNextPlayer();
        }
        
        announceResults();
    }

    private void playTurn(Player player) {
        int diceValue = dice.roll();
        System.out.println("  Rolled: " + diceValue);
        
        Position oldPosition = player.getPosition();
        Position newPosition = board.getNewPosition(oldPosition, diceValue);
        
        player.setPosition(newPosition);
        
        if (newPosition.getValue() != oldPosition.getValue() + diceValue) {
            // Snake or ladder was encountered (already printed in board.getNewPosition)
        } else {
            System.out.println("  Moved to position: " + newPosition.getValue());
        }
    }

    private void handleWinner(Player player) {
        System.out.println("\n🎉 " + player.getName() + " has WON the game! 🎉");
        player.setActive(false);
    }

    private boolean shouldContinue() {
        long activePlayers = players.stream().filter(Player::isActive).count();
        return activePlayers >= 2;
    }

    private void moveToNextPlayer() {
        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } while (!players.get(currentPlayerIndex).isActive() && shouldContinue());
    }

    private void displayPlayers() {
        System.out.println("Players:");
        for (Player player : players) {
            System.out.println("  - " + player.getName());
        }
        System.out.println();
    }

    private void announceResults() {
        System.out.println("\n=== Game Over ===");
        
        List<Player> winners = new ArrayList<>();
        List<Player> remaining = new ArrayList<>();
        
        for (Player player : players) {
            if (player.getPosition().getValue() == board.getMaxPosition()) {
                winners.add(player);
            } else if (player.isActive()) {
                remaining.add(player);
            }
        }
        
        System.out.println("\nWinners:");
        for (Player winner : winners) {
            System.out.println("  🏆 " + winner.getName());
        }
        
        if (!remaining.isEmpty()) {
            System.out.println("\nRemaining Player:");
            for (Player player : remaining) {
                System.out.println("  - " + player.getName() + " (Position: " + 
                                 player.getPosition().getValue() + ")");
            }
        }
    }
}
