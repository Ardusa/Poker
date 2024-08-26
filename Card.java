import java.util.Random;

public class Card {
    private static Random random = new Random();
    private Suit suit;
    private int number;
    private Face face;
    private boolean isFace;
    private int playerHash;
    public enum Suit {
        SPADES,
        CLUBS,
        HEARTS,
        DIAMONDS
    }

    public enum Face {
        JACK,
        QUEEN,
        KING,
        ACE
    }

    /** Specific Card */
    public Card(Suit suit, int number) {
        this.suit = suit;
        this.number = number;
        if (number > 10) {
            isFace = true;
            switch (number) {
                case 11:
                    face = Face.JACK;
                    break;
                case 12:
                    face = Face.QUEEN;
                    break;
                case 13:
                    face = Face.KING;
                    break;
                case 14:
                    face = Face.ACE;
                    break;
                default:
                    System.out.println("Card.Card:31\tError: Invalid Card Number");
                    System.exit(1);
                    break;
            }
        } else {
            isFace = false;
        }
    }
    
    /** Random Card */
    public Card() {
        this(randomSuit(), random.nextInt(14) + 1);
    }

    @Override
    public String toString() {
        if (isFace) {
            return face + " of " + suit;
        } else {
            return number + " of " + suit;
        }
    }

    public String getSuit() {
        return suit.toString();
    }

    public int getNumber() {
        return number;
    }

    public String getFace() {
        return face.toString();
    }

    public boolean isFace() {
        return isFace;
    }

    private static Suit randomSuit() {
        switch (random.nextInt(4) + 1) {
            case 1:
                return Suit.HEARTS;
            case 2:
                return Suit.DIAMONDS;
            case 3:
                return Suit.CLUBS;
            case 4:
                return Suit.SPADES;
            default:
                return null;
        }
    }

    public int getPlayerHash() {
        return playerHash;
    }

    public void setPlayerHash(int playerHash) {
        this.playerHash = playerHash;
    }

    public Player getPlayer() {
        for (Player player : Game.players) {
            if (player.hashCode() == playerHash) {
                return player;
            }
        }
        return new Player("Dealer");
    }
}