import java.util.*;

/**
 * Main entry point for the Snakes & Ladders game
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   Welcome to Snakes & Ladders!    ║");
        System.out.println("╚════════════════════════════════════╝\n");
        
        // Get board size
        System.out.print("Enter board size (n for n x n board): ");
        int n = scanner.nextInt();
        
        // Get number of players
        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Get difficulty level
        System.out.print("Enter difficulty level (easy/hard): ");
        String difficulty = scanner.nextLine().toLowerCase();
        
        // Create players
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }
        
        // Create dice based on difficulty
        DiceStrategy diceStrategy = difficulty.equals("hard") ? new HardDice() : new EasyDice();
        Dice dice = new Dice(diceStrategy);
        
        System.out.println("\nDifficulty: " + difficulty.toUpperCase());
        
        // Initialize board with n snakes and n ladders
        BoardInitializer initializer = new BoardInitializer();
        Board board = initializer.createBoard(n, n, n);
        
        // Create and start game
        GameEngine game = new GameEngine(board, players, dice);
        game.start();
        
        scanner.close();
    }
}
