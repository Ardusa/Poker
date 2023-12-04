import java.util.Scanner;
import java.util.Vector;

public class Game {
    public static Deck deck;
    public static Scanner scanner;
    private static int numberOfPlayers;
    public static Player[] players;
    private static Game instance;
    public boolean testMode;
    private static int i = 0;


    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    public static void main(String[] args) {
        clearScreen();
        deck = Deck.getInstance();
        scanner = new Scanner(System.in);
        numberOfPlayers = getPlayers();

        if (numberOfPlayers < 0 || numberOfPlayers > 8) {
            System.out.println("Dealer.Dealer:31\tError: Invalid Number of Players");
            numberOfPlayers = getPlayers();
        }

        if (numberOfPlayers == 1) {
            System.out.println();
            System.out.println("Test mode");
            System.out.println("Everyones passwords is '1'");
            numberOfPlayers = 4;
            players = new Player[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                players[i] = new Player("Player " + (i + 1));
                players[i].setPassword("1");
            }

            // players[0] = new Player("Player 1");
            // players[0].setPassword("1");
            // players[1] = new Player("Player 2");
            // players[1].setPassword("1");
            // players[2] = new Player("Player 3");
            // players[2].setPassword("1");
        } else {
            players = new Player[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                System.out.print("Enter player #" + (i + 1) + " name: ");
                String name = scanner.next();
                players[i] = new Player(name);
                players[i].setPassword();
            }
        }


        while (true) {
            System.out.println();
            System.out.println(Constants.blankLine);
            System.out.print("Ready to deal? (press any key other than 'n' to keep playing): ");
            String input = scanner.next().toLowerCase();
            System.out.println();
            if (input.equals("n")) {
                // TODO: Establish a winner
                break;
            }
            new Round(i);
            i++;
        }
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static int getPlayers() {
        int x;
        System.out.print("How many people are playing: ");
        try {
            x = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Dealer.Dealer:31\tError: Too many people");
            x = getPlayers();
        }
        return x;
    }

    public Player whoWon() {
        Vector<Hand> hands = new Vector<Hand>();
        // Hand[] hands = new Hand[Game.getInstance().getNumberOfPlayers()];
        for (Player players : players) {
            hands.add(players.getHand());
        }
        return null;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
