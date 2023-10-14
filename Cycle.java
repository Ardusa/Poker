package Poker;

import java.util.Scanner;

public class Cycle {
    private Scanner scanner;
    private double potVal;
    private double callVal;
    private int agreeCount;
    private int foldedCount;
    private int playerCount;

    /** 4 cycles happen per round */
    public Cycle(int foldedCount, int playerCount, Player[] players) {
        scanner = new Scanner(System.in);
        potVal = 0;
        agreeCount = 0;
        this.foldedCount = foldedCount;
        this.playerCount = playerCount;

        do {
            for (int i = 0; i < playerCount; i++) {
                if (turnsRemaining() == 0) {
                    break;
                }
                potVal += playerTurn(players[i]);
            }
        } while (turnsRemaining() > 0);
    }

    private double playerTurn(Player p) {
        if (p.folded) {
            return 0;
        }
        System.out.print(p.toString() + " would you like to fold? (y/n): ");
        if (scanner.nextLine().toLowerCase() == "n") {
            System.out.println("test");
            System.out.print(p.toString() + " would you like to raise? (y/n): ");
            if (scanner.nextLine().toLowerCase() == "n") {
                agreeCount++;
                return p.call(callVal);
            } else if (scanner.nextLine().toLowerCase() == "y") {
                agreeCount = 1;
                return p.requestBet();
            } else {
                System.out.println("Invalid Input");
                return playerTurn(p);
            }
        } else if (scanner.nextLine().toLowerCase() == "y") {
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
