import java.util.Collections;
import java.util.Vector;

public class Dealer extends Player {
    private static Dealer dealer;
    private Card[] cards = new Card[5];
    private static Hand dealerHand;
    // private Card highCard;

    public static Dealer getInstance() {
        if (dealer == null) {
            dealer = new Dealer();
        }
        return dealer;
    }

    public Dealer() {
        super("Dealer");
        // highCard = new Card(Card.Suit.SPADES, 0);
    }

    public void deal() {
        for (int j = 0; j < Game.getInstance().getNumberOfPlayers(); j++) {
            for (int i = 0; i < 2; i++) {
                Game.players[j].getHand().cards[i] = Deck.getInstance().draw();
            }
        }
        for (int i = 0; i < Constants.dealerCards + 3; i++) {
            if (i == 0 || i == 4 || i == 6) {
                Deck.getInstance().draw();
            } else {
                if (i < 4) {
                    cards[i - 1] = Deck.getInstance().draw();
                    continue;
                }
                if (i == 5) {
                    cards[i - 2] = Deck.getInstance().draw();
                    continue;
                }
                if (i == 7) {
                    cards[i - 3] = Deck.getInstance().draw();
                    continue;
                }
            }

            
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
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Vector<Integer> getNumbersFromDealer() {
        Vector<Integer> numbers = new Vector<Integer>();
        for (Card card : cards) {
            numbers.add(card.getNumber());
        }
        Collections.sort(numbers);
        return numbers;
    }

    public Vector<String> getSuitsFromDealer() {
        Vector<String> suits = new Vector<String>();
        for (Card card : cards) {
            suits.add(card.getSuit());
        }
        Collections.sort(suits);
        return suits;
    }
}
