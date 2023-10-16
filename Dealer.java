package Poker;
import java.util.Scanner;

public class Dealer {
    private Scanner scanner;
    private int numberOfPlayers;
    public Player[] players;
    public Card[] cards = new Card[5];
    private final int cardsInHand = 5;

    public Dealer() {
        scanner = new Scanner(System.in);
        numberOfPlayers = getPlayers();

        if (numberOfPlayers < 2 || numberOfPlayers > 8) {
            System.out.println("Dealer.Dealer():31\tError: Invalid Number of Players");
            numberOfPlayers = getPlayers();
        }
        // System.out.print("How many people are playing: ");
        // try {
        //     numberOfPlayers = scanner.nextInt();
        //     // System.out.println(numberOfPlayers);
        // } catch (Exception e) {
        //     System.out.println("Dealer.Dealer():31\tError: Invalid Input");
        //     System.exit(1);
        // }

        // if (numberOfPlayers < 2 || numberOfPlayers > 8) {
        //     System.out.println("Dealer.Dealer():31\tError: Invalid Number of Players");
        // }

        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.print("Enter player #" + (i + 1) + " name: ");
            String name = scanner.next();
            players[i] = new Player(name);
        }
    }

    public void deal() {
        for (int j = 0; j < numberOfPlayers; j++) {
            for (int i = 0; i < 2; i++) {
                players[j].getHand().cards[i] = new Card();
            }
        }
        for (int i = 0; i < cardsInHand; i++) {
            cards[i] = new Card();
        }
    }

    public Card[] getFlop() {
        Card[] flop = new Card[3];
        for (int i = 0; i < 3; i++) {
            flop[i] = cards[i];
        }
        return flop;
    }

    public Card getCard(int cardNumber) {
        if (cardNumber < 1 || cardNumber > 5) {
            System.out.println("Dealer.getCard():31\tError: Invalid Card Number");
            System.exit(1);
        }
        return cards[cardNumber];
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getPlayers() {
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

    public void showCards(int cycleNum) {
        cycleNum += 1;

        System.out.println("----------------------------------------------------------------------");
        System.out.println();
        if (cycleNum == 1) {
            System.out.println("The flop is: ");
            for (int i = 0; i < 3; i++) {
                System.out.println();
                System.out.println(cards[i].toString());
                System.out.println();
            }
        } else if (cycleNum == 2) {
            System.out.println("The turn is: ");
            System.out.println(cards[3].toString());
        } else if (cycleNum == 3) {
            System.out.println("The river is: ");
            System.out.println(cards[4].toString());
        } else {
            System.out.println("Dealer.showCards():31\tError: Invalid Cycle Number");
            System.exit(1);
        }
        System.out.println();
        System.out.println("----------------------------------------------------------------------");

    }

}
