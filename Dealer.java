public class Dealer extends Player {
    private static Dealer dealer;
    public Card[] cards = new Card[5];
    public Player[] players;
    private static Hand dealerHand;

    public static Dealer getInstance() {
        if (dealer == null) {
            dealer = new Dealer();
        }
        return dealer;
    }

    public Dealer() {
        super("Dealer");
    }

    public void deal() {
        for (int j = 0; j < Game.getInstance().getNumberOfPlayers(); j++) {
            for (int i = 0; i < 2; i++) {
                Game.players[j].getHand().cards[i] = Game.deck.draw();
            }
        }
        for (int i = 0; i < Constants.dealerCards; i++) {
            cards[i] = Game.deck.draw();
        }
        dealerHand = new Hand(cards);
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
            System.out.println("Dealer.getCard:31\tError: Invalid Card Number");
            System.exit(1);
        }
        return cards[cardNumber];
    }



    public void showCards(int cycleNum) {
        cycleNum += 1;

        System.out.println(Constants.blankLine);
        if (cycleNum == 1) {
            System.out.println("The flop is: ");
            System.out.println();
            for (int i = 0; i < 3; i++) {
                System.out.println(cards[i].toString());
            }
        } else if (cycleNum == 2) {
            System.out.println("The turn is: ");
            System.out.println(cards[3].toString());
        } else if (cycleNum == 3) {
            System.out.println("The river is: ");
            System.out.println(cards[4].toString());
        } else {
            System.out.println("Dealer.showCards:31\tError: Invalid Cycle Number");
            System.exit(1);
        }
        System.out.println();
        System.out.println(Constants.blankLine);
    }

    public Hand getDealerHand() {
        return dealerHand;
    }
}
