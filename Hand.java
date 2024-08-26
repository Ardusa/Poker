import java.util.Collections;
import java.util.Vector;

public class Hand {
    public Card[] cards;
    private int cardsInHand = 2;

    public Hand() {
        cards = new Card[2];
        for (int i = 0; i < cardsInHand; i++) {
            cards[i] = Deck.getInstance().draw();
        }
    }

    public Hand(Card[] cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card card = (Card) obj;
            if (card.toString().equals(this.toString())) {
                return true;
            }
        }
        return false;
    }

    public Vector<Integer> getNumbers() {
        Vector<Integer> numbers = new Vector<Integer>();
        for (Card card : cards) {
            numbers.add(card.getNumber());
        }

        numbers.addAll(Dealer.getInstance().getNumbersFromDealer());

        Collections.sort(numbers);
        // System.out.println(numbers);

        return numbers;
    }

    public Vector<String> getSuits() {
        Vector<String> suits = new Vector<String>();
        for (Card card : cards) {
            suits.add(card.getSuit());
        }

        suits.addAll(Dealer.getInstance().getSuitsFromDealer());

        Collections.sort(suits);
        // System.out.println(suits);

        return suits;
    }

    public Card getHighCardFromPlayer() {
        Card highCard = cards[0];
        for (Card card : cards) {
            if (card.getNumber() > highCard.getNumber()) {
                highCard = card;
            }
        }

        return highCard;
    }

    /** Returns the true if card 1 is the highest, false if otherwise */
    public static boolean compareCards(int card1, int card2) {
        if (card1 > card2) {
            return true;
        } else {
            return false;
        }
    }

    public void showCards() {
        for (Card card : cards) {
            System.out.println(card.toString());
        }
    }
}