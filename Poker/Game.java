package Poker;

import java.util.Scanner;

public class Game {
    public static Deck deck;
    public static Scanner scanner;
    private static int numberOfPlayers;
    public static Player[] players;
    private static Game instance;
    private static final int rounds = 1;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    public static void main(String[] args) {
        deck = Deck.getInstance();
        scanner = new Scanner(System.in);
        numberOfPlayers = getPlayers();

        if (numberOfPlayers < 2 || numberOfPlayers > 8) {
            System.out.println("Dealer.Dealer():31\tError: Invalid Number of Players");
            numberOfPlayers = getPlayers();
        }

        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.print("Enter player #" + (i + 1) + " name: ");
            String name = scanner.next();
            players[i] = new Player(name);
            players[i].setPassword();
        }

        for (int i = 0; i < rounds; i++) {
            new Round();
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
            System.out.println("Dealer.Dealer():31\tError: Invalid Input");
            x = getPlayers();
        }
        return x;
    }
}
