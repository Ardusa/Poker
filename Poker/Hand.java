package Poker;

public class Hand {
    public Card[] cards = new Card[2];
    private int cardsInHand = 2;

    public Hand() {
        for (int i = 0; i < cardsInHand; i++) {
            cards[i] = Game.deck.draw();
        }
    }

}
