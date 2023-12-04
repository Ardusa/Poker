import java.util.Scanner;
import java.util.Vector;

public class Cycle {
    private Scanner scanner;
    private double potVal;
    private double callVal;
    private int agreeCount;
    private int foldedCount;
    private int playerCount;
    public boolean gameOver;
    public Player winner;
    public Vector<Player> hasCalled;
    

    private String fold;
    private String raise;

    /** 4 cycles happen per round */
    public Cycle(int foldedCount, int playerCount, Player[] players, int roundNumber, int cycleNumber) {
        scanner = new Scanner(System.in);
        if (cycleNumber == 0) {
            potVal = Constants.bigBlind + Constants.smallBlind;
            callVal = Constants.bigBlind;
        } else {
            potVal = 0;
            callVal = 0;
        }
        agreeCount = 0;
        this.foldedCount = foldedCount;
        this.playerCount = playerCount;

        hasCalled = new Vector<Player>();

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
                
                if (!players[i].folded && !players[i].isAllIn() && !hasCalled.contains(players[i])) {
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
        System.out.println("Call Value: $" + callVal + "\nPot Value: $" + potVal + "\n" + p.toString()
                + " personal pot value: $" + p.personalPotValue);
        

        if (p.folded) {
            return 0;
        }

        p.showCards();
        if (callVal != 0 && callVal != p.personalPotValue) {
            System.out.println("Price to call is $" + (callVal - p.personalPotValue));
        }

        if (callVal == p.personalPotValue) {
            fold = "n";
        } else {
            System.out.print("Would you like to fold? (y/n): ");
            fold = scanner.next().toLowerCase();
        }

        if (fold.equals("n")) {
            System.out.print(p.toString() + " would you like to raise? (y/n): ");
            raise = scanner.next().toLowerCase();
            System.out.println();
            if (raise.equals("n")) {
                agreeCount++;
                hasCalled.add(p);
                if (callVal == p.personalPotValue) {
                    System.out.println(p.toString() + " checked");
                    return 0;
                }
                System.out.println(p.toString() + " called $" + (callVal - p.personalPotValue));
                return p.call(callVal);

            } else if (raise.equals("y")) {
                agreeCount = 1;
                System.out.println("You have $" + p.getMoney());
                double raiseVal = p.requestBet();
                hasCalled.clear();
                hasCalled.add(p);
                callVal += raiseVal;
                p.personalPotValue += raiseVal;
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

    public double getCallVal() {
        return callVal;
    }

    public void setCallVal(double callVal) {
        this.callVal = callVal;
    }
}