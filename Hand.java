import java.util.Vector;

public class Hand {
    public Card[] cards;
    private int cardsInHand = 2;
    private int valueOfPair;
    // private Card highCard;
    private Vector<HandValue> currValue = new Vector<HandValue>();

    public Hand() {
        cards = new Card[2];
        for (int i = 0; i < cardsInHand; i++) {
            cards[i] = Game.deck.draw();
        }

        Constants.handValues.handsWithHighCard.add(HandValue.PAIR);
        Constants.handValues.handsWithHighCard.add(HandValue.TWO_PAIR);
        Constants.handValues.handsWithHighCard.add(HandValue.THREE_OF_A_KIND);
        Constants.handValues.handsWithHighCard.add(HandValue.FOUR_OF_A_KIND);
        Constants.handValues.handsWithHighCard.add(HandValue.NOTHING);
    }

    public Hand(Card[] cards) {
        this.cards = cards;
    }
    
    public enum args {
        NEWVECTOR,
        SecondPAIR,
        Royal,
        NONE
    }

    public enum HandValue {
        HIGH_CARD,
        PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH,
        NOTHING
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

    public int valueOfHand(Player p) {
        int value = 0;

        if (p.folded) {
            return 0;
        }else if (p.getHand().royalFlush().equals(HandValue.ROYAL_FLUSH)) {
            value += Constants.handValues.royalFlushValue;
            currValue.add(HandValue.ROYAL_FLUSH);
        } else if (p.getHand().straightFlush().equals(HandValue.STRAIGHT_FLUSH)) {
            value += Constants.handValues.straightFlushValue;
            currValue.add(HandValue.STRAIGHT_FLUSH);
        } else if (p.getHand().checkForPairs(args.NONE).equals(HandValue.FOUR_OF_A_KIND)) { // Check for four pair
            value += Constants.handValues.fourOfAKindValue;
            currValue.add(HandValue.FOUR_OF_A_KIND);
        } else if (p.getHand().flush().equals(HandValue.FLUSH)) {
            value += Constants.handValues.flushValue;
            currValue.add(HandValue.FLUSH);
        } else if (p.getHand().straight(args.NONE).equals(HandValue.STRAIGHT)) {
            value += Constants.handValues.straightValue;
            currValue.add(HandValue.STRAIGHT);
        } else if (p.getHand().checkForPairs(args.NONE)==HandValue.THREE_OF_A_KIND) {
            value += Constants.handValues.threeOfAKindValue;
            p.getHand().getNumbers().lastElement();
            if (p.getHand().checkForPairs(args.SecondPAIR).equals(HandValue.PAIR)) {
                value = Constants.handValues.fullHouseValue;
            }
        } else if (p.getHand().checkForPairs(args.NONE).equals(HandValue.PAIR)) {
            if (p.getHand().checkForPairs(args.SecondPAIR).equals(HandValue.PAIR)) {
                value += Constants.handValues.twoPairValue;
                currValue.clear();
                currValue.add(HandValue.TWO_PAIR);
            } else {
                value += Constants.handValues.pairValue;
                currValue.add(HandValue.PAIR);
            }
            // return Constants.handValues.pairValue;
        } if (currValue.isEmpty()) {
            currValue.add(HandValue.NOTHING);
        }

        if (currValue.size() == 1 || currValue.contains(HandValue.TWO_PAIR)) {
            if (Constants.handValues.handsWithHighCard.contains(currValue.get(0)))
                if (p.getHand().getHighCard().getPlayer().equals(p)) {
                    value += Constants.handValues.highCardValue;
                }
        }

        return value;
    }
    
    public HandValue checkForPairs(args args) {

        Vector<Integer> numbers = new Vector<Integer>();

        switch (args) {
            case NEWVECTOR:
                numbers = new Vector<Integer>();
                break;
            case SecondPAIR:
                for (Card card : cards) {
                    if (card.getNumber() != this.valueOfPair) {
                        numbers.add(card.getNumber());
                    }
                }
                for (Card card : Dealer.getInstance().cards) {
                    if (card.getNumber() != this.valueOfPair) {
                        numbers.add(card.getNumber());
                    }
                }
                break;
            case NONE:
                numbers = this.getNumbers();
                numbers.addAll(Dealer.getInstance().getHand().getNumbers());
                break;
            default:
                System.out.println("Hand.checkForPairs: Error: Invalid args");
                System.exit(1);
                break;
        }

        numbers.sort(null);

        for (int x = 0; x < numbers.size() - 4; x++) { // Check for four of a kind
            if (numbers.get(x) == numbers.get(x + 1) && numbers.get(x + 1) == numbers.get(x + 2)
                    && numbers.get(x + 2) == numbers.get(x + 3)) {
                this.valueOfPair = numbers.get(x);
                return HandValue.FOUR_OF_A_KIND;
            }
        }

        for (int i = 0; i < numbers.size() - 2; i++) {
            if (numbers.get(i) == numbers.get(i + 1)) {
                if (numbers.get(i) == numbers.get(i + 1) && numbers.get(i + 1) == numbers.get(i + 2)) {
                    this.valueOfPair = numbers.get(numbers.get(i));
                    return HandValue.THREE_OF_A_KIND;
                }
            } else if (numbers.get(numbers.size() - 1) == numbers.get(numbers.size() - 2)) {
                this.valueOfPair = numbers.get(numbers.get(numbers.size() - 1));
                return HandValue.PAIR;
            } else {
                continue;
            }
        }
        return HandValue.NOTHING;
    }

    public HandValue flush() {
        Vector<String> suits = new Vector<String>();
        suits.addAll(this.getSuits());
        suits.addAll(Dealer.getInstance().getHand().getSuits());
        suits.sort(null);

        // Vector<Card> cards = new Vector<Card>();

        for (int i = 0; i < suits.size() - 5; i++) {
            if (suits.get(i).equals(suits.get(i + 1)) && suits.get(i + 1).equals(suits.get(i + 2))
                    && suits.get(i + 2).equals(suits.get(i + 3)) && suits.get(i + 3).equals(suits.get(i + 4))
                    && suits.get(i + 4).equals(suits.get(i + 5))) {
                return HandValue.FLUSH;
            }
        }

        return HandValue.NOTHING;
    }

    public HandValue straight(args args) {
        Vector<Integer> numbers = new Vector<Integer>();
        numbers.addAll(this.getNumbers());
        numbers.addAll(Dealer.getInstance().getHand().getNumbers());
        numbers.sort(null);

        for (int i = 0; i < numbers.size() - 5; i++) {
            if (numbers.get(i) + 1 == numbers.get(i + 1) && numbers.get(i + 1) + 1 == numbers.get(i + 2)
                    && numbers.get(i + 2) + 1 == numbers.get(i + 3) && numbers.get(i + 3) + 1 == numbers.get(i + 4)
                    && numbers.get(i + 4) + 1 == numbers.get(i + 5))
            {
                if (args.equals(Hand.args.Royal)) {
                    if (numbers.get(i) == 10) {
                        return HandValue.ROYAL_FLUSH;
                    }
                }
                return HandValue.STRAIGHT;
            }
        }
        return HandValue.NOTHING;
    }

    public HandValue straightFlush() {
        if (this.flush().equals(HandValue.FLUSH) && this.straight(args.NONE).equals(HandValue.STRAIGHT)) {
            return HandValue.STRAIGHT_FLUSH;
        }
        return HandValue.NOTHING;
    }


    public HandValue royalFlush() {
        if (this.flush().equals(HandValue.FLUSH) && this.straight(args.Royal).equals(HandValue.ROYAL_FLUSH)) {
            return HandValue.ROYAL_FLUSH;
        }
        return HandValue.NOTHING;
    }

        public Vector<Integer> getNumbers() {
        Vector<Integer> numbers = new Vector<Integer>();
        for (Card card : cards) {
            numbers.add(card.getNumber());
        }
        return numbers;
    }

    public Vector<String> getSuits() {
        Vector<String> suits = new Vector<String>();
        for (Card card : cards) {
            suits.add(card.getSuit());
        }
        return suits;
    }

    private Card getHighCardFromPlayer() {
        Card highCard = cards[0];
        for (Card card : cards) {
            if (card.getNumber() > highCard.getNumber()) {
                highCard = card;
            }
        }

        return highCard;
    }

    private Card getHighCardFromDealer() {
        Card highCard = Dealer.getInstance().getHand().cards[0];
        for (Card card : Dealer.getInstance().getHand().cards) {
            if (card.getNumber() > highCard.getNumber()) {
                highCard = card;
            }
        }

        return highCard;
    }

    public Card getHighCard() {
        return compareCards(getHighCardFromPlayer(), getHighCardFromDealer());
    }

    /* Returns the highest number card */
    public Card compareCards(Card... cards) {
        Card highCard = cards[0];
        for (Card card : cards) {
            if (card.getNumber() > highCard.getNumber()) {
                highCard = card;
            }
        }
        return highCard;
    }

    // public HandValue flush(Card card1, Card card2, Card card3, Card card4, Card card5) {
    //     if (card1.getSuit().equals(card2.getSuit()) && card2.getSuit().equals(card3.getSuit())
    //             && card3.getSuit().equals(card4.getSuit()) && card4.getSuit().equals(card5.getSuit())) {
    //         return HandValue.FLUSH;
    //     }
    //     return HandValue.NOTHING;
    // }

    // public HandValue straight(Card card1, Card card2, Card card3, Card card4, Card card5) {
    //     Vector<Card> cards = new Vector<Card>();
    //     cards.add(card1);
    //     cards.add(card2);
    //     cards.add(card3);
    //     cards.add(card4);
    //     cards.add(card5);
    //     cards.sort(null);

    //     for (int i = 0; i < cards.size() - 1; i++) {
    //         if (cards.get(i).getNumber() + 1 != cards.get(i + 1).getNumber()) {
    //             return HandValue.NOTHING;
    //         }
    //     }
    //     return HandValue.STRAIGHT;
    // }

    // /** Returns enum if card1 is more than card2 */
    // public HandValue highCard(Card card1, Card card2) {
    //     if (card1.getNumber() > card2.getNumber()) {
    //         return HandValue.HIGH_CARD;
    //     }
    //     return HandValue.NOTHING;
    // }

    // public HandValue fullHouse(Card card1, Card card2, Card card3, Card card4, Card card5) {
    //     for (int i = 0; i < 3; i++) {
    //         if (card1.getNumber() == card2.getNumber() && card2.getNumber() == card3.getNumber()) {
    //             if (card4.getNumber() == card5.getNumber()) {
    //                 return HandValue.FULL_HOUSE;
    //             }
    //         } else if (card3.getNumber() == card4.getNumber() && card4.getNumber() == card5.getNumber()) {
    //             if (card1.getNumber() == card2.getNumber()) {
    //                 return HandValue.FULL_HOUSE;
    //             }
    //         }
    //     }
    //     return HandValue.NOTHING;
    // }

    // public HandValue fourOfAKind(Card card1, Card card2, Card card3, Card card4, Card card5) {
    //     for (int i = 0; i < 2; i++) {
    //         if (card1.getNumber() == card2.getNumber() && card2.getNumber() == card3.getNumber()
    //                 && card3.getNumber() == card4.getNumber()) {
    //             return HandValue.FOUR_OF_A_KIND;
    //         } else if (card2.getNumber() == card3.getNumber() && card3.getNumber() == card4.getNumber()
    //                 && card4.getNumber() == card5.getNumber()) {
    //             return HandValue.FOUR_OF_A_KIND;
    //         }
    //     }
    //     return HandValue.NOTHING;
    // }

    // // public HandValue straightFlush(Card card1, Card card2, Card card3, Card card4, Card card5) {
    // //     if (card1.getSuit().equals(card2.getSuit()) && card2.getSuit().equals(card3.getSuit())
    // //             && card3.getSuit().equals(card4.getSuit()) && card4.getSuit().equals(card5.getSuit())) {
    // //         Vector<Card> cards = new Vector<Card>();
    // //         cards.add(card1);
    // //         cards.add(card2);
    // //         cards.add(card3);
    // //         cards.add(card4);
    // //         cards.add(card5);
    // //         cards.sort(null);
    // //         for (int i = 0; i < cards.size() - 1; i++) {
    // //             if (cards.get(i).getNumber() + 1 != cards.get(i + 1).getNumber()) {
    // //                 return HandValue.NOTHING;
    // //             }
    // //         }
    // //         return HandValue.STRAIGHT_FLUSH;
    // //     }
    // //     return HandValue.NOTHING;
    // // }

    // public HandValue royalFlush(Card card1, Card card2, Card card3, Card card4, Card card5) {
    //     if (card1.getSuit().equals(card2.getSuit()) && card2.getSuit().equals(card3.getSuit())
    //             && card3.getSuit().equals(card4.getSuit()) && card4.getSuit().equals(card5.getSuit())) {
    //         Vector<Card> cards = new Vector<Card>();
    //         cards.add(card1);
    //         cards.add(card2);
    //         cards.add(card3);
    //         cards.add(card4);
    //         cards.add(card5);
    //         cards.sort(null);
    //         if (cards.get(0).getNumber() == 10 && cards.get(1).getNumber() == 11 && cards.get(2).getNumber() == 12
    //                 && cards.get(3).getNumber() == 13 && cards.get(4).getNumber() == 14) {
    //             return HandValue.ROYAL_FLUSH;
    //         }
    //     }
    //     return HandValue.NOTHING;
    // }

    /** Sort by number */
    // public Hand sortHand(Hand hand) {
    //     Hand sortedHand = new Hand(HandValue.cards);
    //     Vector<Card> sortedCards = new Vector<Card>();
    //     for (Card card : HandValue.cards) {
    //         sortedCards.add(card);
    //     }

    //     sortedCards.sort((Card a, Card b) -> {
    //         return a.getNumber() - b.getNumber();
    //     });

    //     // for (int i = 0; i < cardsInHand; i++) {
    //     //     if (HandValue.cards[i].getNumber() > sortedHandValue.cards[i + 1].getNumber()) {
    //     //         sortedHandValue.cards[i] = HandValue.cards[i];
    //     //     } else {
    //     //         sortedHandValue.cards[i] = HandValue.cards[i + 1];
    //     //     }
    //     // }
    //     // return sortedHand;
    // }
}