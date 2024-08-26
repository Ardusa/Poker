import java.util.Scanner;

public class Game {
    public static Deck deck;
    public static Scanner scanner;
    private static int numberOfPlayers;
    public static Player[] players;
    private static Game instance;
    private static int roundNumber = 0;

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

        setNumberOfPlayers(Constants.isDebug);

        while (true) {
            System.out.println();
            System.out.println(Constants.blankLine);
            new Round(roundNumber);
            System.out.print("Ready to deal? (press any key other than 'n' to keep playing): ");
            String input = scanner.next().toLowerCase();
            System.out.println();
            if (input.equals("n")) {
                System.out.println("Thanks for playing!");
                for (Player player : players) {
                    double winnings = player.getMoney() - Constants.startingMoney;
                    System.out.println(player.toString() + " went " + ((winnings > 0) ? "positive" : "negative") + " $"
                            + winnings);
                }
                break;
            }

            roundNumber++;
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
            System.out.println("Dealer.Dealer:31\tError: Invalid Number of Players");
            x = getPlayers();
        }
        return x;
    }

    public void whoWon() {
        // Vector<Hand> hands = new Vector<Hand>();
        // for (Player players : players) {
        // hands.add(players.getHand());
        // }
        // return null;

        for (Player player : players) {
            System.out.println(player.toString() + "'s Value: " + player.getHandValue().getValue());
            System.out.println(player.getHandValue().getHandStats());
        }
        // return null;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // System.out.println("\n\nSupposed to Clear Screen\n\n");
    }

    public static void setNames() {
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.print("Enter player #" + (i + 1) + " name: ");
            String name = scanner.next();
            if (name.equals("Dealer")) {
                System.out.println("Dealer.Dealer:31\tError: Invalid Name");
                setNames();
            }

            if (name.equals("")) {
                System.out.println("Dealer.Dealer:31\tError: Invalid Name");
                setNames();
            }

            players[i] = new Player(name);
            players[i].setPassword();
        }
    }

    public static void setNumberOfPlayers(boolean testMode) {
        if (testMode) {
            System.out.println();
            System.out.println("Test mode");
            System.out.println("Everyones passwords is '1'");
            numberOfPlayers = 4;
            players = new Player[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                players[i] = new Player("Player " + (i + 1));
                players[i].setPassword("1");
            }

        }

        else {
            numberOfPlayers = getPlayers();
            if (numberOfPlayers < 3 || numberOfPlayers > 8) {
                System.out.println(Constants.blankLine);
                System.out.println("Dealer.Dealer:31\tError: Invalid Number of Players");
                System.out.println(Constants.blankLine);
                setNumberOfPlayers(testMode);
            }

            else {
                players = new Player[numberOfPlayers];
                setNames();
            }

        }
    }
}
