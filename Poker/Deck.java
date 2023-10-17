package Poker;
import java.util.Vector;

public class Deck {
    private Vector<Card> cards = new Vector<Card>();
    private static Deck instance;

    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = 2; i < 15; i++) {
                cards.add(new Card(suit, i));
            }
        }
    }
    
    public Card draw() {
        int index = (int) (Math.random() * cards.size());
        Card card = cards.get(index);
        cards.remove(index);
        return card;
    }
}
