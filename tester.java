public class tester {
    public static void main(String[] args) {
        Game.clearScreen();
        System.out.println("Testing Scoring Algorithm");
        Deck.getInstance();
        Dealer dealer = Dealer.getInstance();
        Game.setNumberOfPlayers(true);
        System.out.println(Constants.blankLine);
        dealer.deal();

        System.out.println();
        System.out.println();
        System.out.println("Dealer's Hand");
        Dealer.getInstance().getDealerHand().showCards();
        System.out.println();

        for (Player player : Game.players) {
            player.showCards();
            System.out.println();
        }

        // Game.players[0].showCards();
        // System.out.println();
        // Game.players[0].getHand().valueOfHand(Game.players[0]);


        Game.getInstance().whoWon();
    }
}
