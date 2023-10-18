package Poker;

import java.util.Scanner;

public class Cycle {
    private Scanner scanner;
    private double potVal;
    private double callVal;
    private int agreeCount;
    private int foldedCount;
    private int playerCount;

    private String fold;
    private String raise;

    /** 4 cycles happen per round */
    public Cycle(int foldedCount, int playerCount, Player[] players) {
        scanner = new Scanner(System.in);
        potVal = 0;
        callVal = 10;
        agreeCount = 0;
        this.foldedCount = foldedCount;
        this.playerCount = playerCount;

        do {
            for (int i = 0; i < playerCount; i++) {
                System.out.println("----------------------------------------");
                System.out.println(players[i].toString() + "'s Turn");
                System.console().flush();
                if (turnsRemaining() == 0) {
                    break;
                }

                // do {
                //     pw = players[i].getPassword();
                // } while (players[i].checkPassword(pw));

                players[i].getAndCheckPW();

                potVal += playerTurn(players[i]);
                System.out.println();
            }
        } while (turnsRemaining() > 0);
    }

    private double playerTurn(Player p) {
        if (p.folded) {
            return 0;
        }

        p.showCards();

        System.out.print("Would you like to fold? (y/n): ");
        fold = scanner.next().toLowerCase();

        if (fold.equals("n")) {
            System.out.println("Price to call is $" + callVal);
            System.out.print(p.toString() + " would you like to raise? (y/n): ");
            raise = scanner.next().toLowerCase();
            System.out.println();
            if (raise.equals("n")) {
                agreeCount++;
                System.out.println(p.toString() + " called $" + callVal);
                return p.call(callVal);
            } else if (raise.equals("y")) {
                agreeCount = 1;
                callVal = p.requestBet();
                return callVal;
            } else {
                System.out.println("Invalid Input");
                return playerTurn(p);
            }
        } else if (fold.equals("y")) {
            p.folded = true;
            foldedCount++;
            return 0;
        } else {
            System.out.println("Invalid Input");
            return playerTurn(p);
        }
    }

    public int turnsRemaining() {
        return playerCount - (agreeCount + foldedCount);
    }

    public double getRoundPot() {
        return potVal;
    }

    public int getFoldedCount() {
        return foldedCount;
    }
}