public class Round {
     public static Round instance;
    private Dealer dealer = Dealer.getInstance();
    private double potVal;
    private int foldedCount;
    private int playerCount;
    private Cycle currentCycle;

    public enum blind {
        None,
        Small,
        Big
    }

    /** The only thing that should be staying the same between rounds is money, everything else resets */
    public Round(int roundNumber) {
        Player[] newPlayers = Game.players.clone();

        resetFolded();
        potVal = 0;
        for (Player player : newPlayers) {
            foldedCount += player.folded();
            player.personalPotValue = 0;
        }

        playerCount = Game.getInstance().getNumberOfPlayers();

        dealer.deal();

        newPlayers[1].blind = blind.Small;
        newPlayers[2].blind = blind.Big;

        System.out.println("The small blind is " + newPlayers[1].toString());
        System.out.println("The big blind is " + newPlayers[2].toString());

        newPlayers[1].call(Constants.smallBlind);
        newPlayers[2].call(Constants.bigBlind);

        if (roundNumber == 0) {
            for (int x = 0; x != 3; x++) {
                Player temp = newPlayers[0];
                for (int j = 1; j <= newPlayers.length - 1; j++) {
                    newPlayers[j - 1] = newPlayers[j];
                }
                newPlayers[newPlayers.length - 1] = temp;
            }
        }

        for (int cycleNumber = 0; cycleNumber < 4; cycleNumber++) {
            currentCycle = new Cycle(foldedCount, playerCount, newPlayers, roundNumber, cycleNumber);
            if (cycleNumber == 3) {
                currentCycle.gameOver = true;
            }
            if (currentCycle.gameOver) {
                showDown();
                break;
            }
            dealer.showCards(cycleNumber);
            potVal += currentCycle.getPot();
            System.out.println("The pot is $" + potVal);
            foldedCount = currentCycle.getFoldedCount();
            System.out.println();

        }
        
        Player temp = newPlayers[0];
        for (int j = 1; j <= newPlayers.length - 1; j++) {
            newPlayers[j - 1] = newPlayers[j];
        }
        newPlayers[newPlayers.length - 1] = temp;
    }

    private void resetFolded() {
        for (Player player : Game.players) {
            player.folded = false;
        }
    }

    public void showDown() {
        System.out.println(Constants.blankLine);
        Player winner = Game.players[0];
        for (Player p : Game.players) {
            double handValue = p.getHandValue().getValue();
            System.out.println(p.toString() + "'s Hand Value: " + handValue);
            if (handValue > winner.getHandValue().getValue()) {
                winner = p;
            }
            System.out.println("Winner: " + winner.toString());
        }
    }
}
