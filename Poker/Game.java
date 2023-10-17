package Poker;

public class Game {
    public static Deck deck;

    public static void main(String[] args) {
        deck = Deck.getInstance();
        new Round();
    }
}
