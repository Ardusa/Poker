package Poker;

public class Round {
    private Dealer dealer = new Dealer();
    private double potVal;
    private int foldedCount;
    private int playerCount;
    private Cycle currentCycle;

    /** The only thing that should be staying the same between rounds is money, everything else resets */
    public Round() {
        resetFolded();
        potVal = 0;
        for (Player player : dealer.players) {
            foldedCount += player.folded();
        }
        playerCount = dealer.getNumberOfPlayers();

        dealer.deal();
        for (int i = 0; i < 4; i++) {
            currentCycle = new Cycle(foldedCount, playerCount, dealer.players);
            dealer.showCards(i);
            System.out.println();
            potVal += currentCycle.getRoundPot();
            System.out.println("The pot is $" + potVal);
            for (Player player : dealer.players) {
                if (player.folded = false) {
                    System.out.println(player.toString() + " has $" + player.getMoney());
                }
            }
            foldedCount = currentCycle.getFoldedCount();
            System.out.println();
            System.out.println("----------------------------------------------------------------------");
        }
    }

    private void resetFolded() {
        for (Player player : dealer.players) {
            player.folded = false;
        }
    }
}
