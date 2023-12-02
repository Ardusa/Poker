package Poker;

import java.util.Scanner;

public class Cycle {
    private Scanner scanner;
    private double potVal;
    private double callVal;
    private int agreeCount;
    private int foldedCount;
    private int playerCount;
    public boolean gameOver;
    public Player winner;
    

    private String fold;
    private String raise;

    /** 4 cycles happen per round */
    public Cycle(int foldedCount, int playerCount, Player[] players, int roundNumber) {
        scanner = new Scanner(System.in);
        potVal = 0;
        if (roundNumber == 0) {
            callVal = Constants.smallBlind;
        } else {
            callVal = 0;
        }
        agreeCount = 0;
        this.foldedCount = foldedCount;
        this.playerCount = playerCount;

        do {
            for (int i = 0; i < playerCount; i++) {
                if (foldedCount == playerCount - 1) {
                    gameOver = true;
                    for (Player player : players) {
                        if (!player.folded) {
                            winner = player;
                        }
                    }
                    break;
                }
                
                if (!players[i].folded && !players[i].isAllIn()) {
                    System.out.println(Constants.blankLine);
                    System.out.println(players[i].toString() + "'s Turn");
                    players[i].getAndCheckPW();
                    potVal += playerTurn(players[i]);
                    System.out.println();
                }

                if (players[i].isAllIn()) {
                    System.out.println(players[i].toString() + " is all in");
                }
            }
        } while (turnsRemaining() > 0);
    }

    private double playerTurn(Player p) {
        p.personalPotValue = 0;
        if (p.folded) {
            return 0;
        }

        // if (p.isAllIn()) {
        //     System.out.println(p.toString() + " is all in");
        //     return 0;
        // }

        p.showCards();
        if (callVal != 0) {
            System.out.println("Price to call is $" + callVal);
        }
        System.out.print("Would you like to fold? (y/n): ");
        fold = scanner.next().toLowerCase();

        if (fold.equals("n")) {
            p.personalPotValue += callVal;
            // System.out.println("Price to call is $" + callVal);
            System.out.print(p.toString() + " would you like to raise? (y/n): ");
            raise = scanner.next().toLowerCase();
            System.out.println();
            if (raise.equals("n")) {
                agreeCount++;
                if (callVal == 0) {
                    System.out.println(p.toString() + " checked");
                } else {
                    System.out.println(p.toString() + " called $" + callVal);
                }
                p.call(callVal);
                return callVal;
            } else if (raise.equals("y")) {
                agreeCount = 1;
                System.out.println("You have $" + p.getMoney());
                double raiseVal = p.requestBet();
                p.personalPotValue += raiseVal;
                callVal += raiseVal;
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