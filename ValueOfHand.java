import java.util.Vector;

public class ValueOfHand {
    private Player player;
    private boolean isHighCard;
    private Card highCard;
    private int value;
    private int valueOfPair;

    private Vector<HandValue> currValue = new Vector<HandValue>();

    public enum args {
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

    public ValueOfHand(Player player) {
        this.player = player;
        value = 0;

        Constants.handValues.handsWithHighCard.add(HandValue.PAIR);
        Constants.handValues.handsWithHighCard.add(HandValue.TWO_PAIR);
        Constants.handValues.handsWithHighCard.add(HandValue.THREE_OF_A_KIND);
        Constants.handValues.handsWithHighCard.add(HandValue.FOUR_OF_A_KIND);
        Constants.handValues.handsWithHighCard.add(HandValue.NOTHING);

        if (player.folded) {
            value = 0;
        } else if (royalFlush().equals(HandValue.ROYAL_FLUSH)) {
            value += Constants.handValues.royalFlushValue;
            currValue.add(HandValue.ROYAL_FLUSH);
        } else if (straightFlush().equals(HandValue.STRAIGHT_FLUSH)) {
            value += Constants.handValues.straightFlushValue;
            currValue.add(HandValue.STRAIGHT_FLUSH);
        } else if (checkForPairs(args.NONE).equals(HandValue.FOUR_OF_A_KIND)) { // Check for four pair
            value += Constants.handValues.fourOfAKindValue;
            currValue.add(HandValue.FOUR_OF_A_KIND);
        } else if (flush().equals(HandValue.FLUSH)) {
            value += Constants.handValues.flushValue;
            currValue.add(HandValue.FLUSH);
        } else if (straight(args.NONE).equals(HandValue.STRAIGHT)) {
            value += Constants.handValues.straightValue;
            currValue.add(HandValue.STRAIGHT);
        } else if (checkForPairs(args.NONE) == HandValue.THREE_OF_A_KIND) {
            value += Constants.handValues.threeOfAKindValue;
            if (checkForPairs(args.SecondPAIR).equals(HandValue.PAIR)) {
                value = Constants.handValues.fullHouseValue;
                currValue.add(HandValue.FULL_HOUSE);
            }
        } else if (checkForPairs(args.NONE).equals(HandValue.PAIR)) {
            if (checkForPairs(args.SecondPAIR).equals(HandValue.PAIR)) {
                value += Constants.handValues.twoPairValue;
                currValue.clear();
                currValue.add(HandValue.TWO_PAIR);
            } else {
                value += Constants.handValues.pairValue;
                currValue.add(HandValue.PAIR);
            }
        }
        if (currValue.isEmpty()) {
            currValue.add(HandValue.NOTHING);
        }

        if (Constants.handValues.handsWithHighCard.contains(currValue.get(0))) {
            System.out.println("Checking for high card");
            if (Constants.handValues.handsWithHighCard.contains(currValue.get(0))) {
                if (getHighCard()) {
                    value += Constants.handValues.highCardValue;
                    currValue.add(HandValue.HIGH_CARD);
                }
            }
        }
    }

    public HandValue checkForPairs(args args) {
        Vector<Integer> numbers = new Vector<Integer>();
        switch (args) {
            case SecondPAIR:
                for (Integer inte : player.getHand().getNumbers()) {
                    if (inte != this.valueOfPair) {
                        numbers.add(inte);
                    }
                }
                for (Integer inte : Dealer.getInstance().getNumbersFromDealer()) {
                    if (inte != this.valueOfPair) {
                        numbers.add(inte);
                    }
                }
                break;
            case NONE:
                numbers = player.getHand().getNumbers();
                break;
            default:
                System.out.println("Hand.checkForPairs: Error: Invalid args");
                System.exit(1);
                break;
        }

        for (int x = 0; x < numbers.size() - 3; x++) { // Check for four of a kind
            if (numbers.get(x) == numbers.get(x + 1) && numbers.get(x + 1) == numbers.get(x + 2)
                    && numbers.get(x + 2) == numbers.get(x + 3)) {
                this.valueOfPair = numbers.get(x);
                return HandValue.FOUR_OF_A_KIND;
            } else {
                // System.out.println("No four of a kind");
            }
        }

        for (int i = 0; i < numbers.size() - 2; i++) {
            if (numbers.get(i) == numbers.get(i + 1)) {
                if (numbers.get(i) == numbers.get(i + 1) && numbers.get(i + 1) == numbers.get(i + 2)) {
                    this.valueOfPair = numbers.get(i);
                    return HandValue.THREE_OF_A_KIND;
                } else {
                    // System.out.println("No three of a kind, only pair");
                    this.valueOfPair = numbers.get(i);
                    return HandValue.PAIR;
                }
            } else {
                // System.out.println("No pair");
                continue;
            }
        }
        // if (numbers.get(numbers.size() - 1) == numbers.get(numbers.size() - 2)) {
        //     this.valueOfPair = numbers.get(numbers.size() - 1);
        //     return HandValue.PAIR;
        // }
        return HandValue.NOTHING;
    }

    public HandValue flush() {
        Vector<String> suits = new Vector<String>();
        suits = player.getHand().getSuits();

        // System.out.println(suits);

        for (int i = 0; i < suits.size() - 5; i++) {
            if (suits.get(i).equals(suits.get(i + 1)) && suits.get(i + 1).equals(suits.get(i + 2))
                    && suits.get(i + 2).equals(suits.get(i + 3)) && suits.get(i + 3).equals(suits.get(i + 4))) {
                return HandValue.FLUSH;
            } else {
            }
        }

        return HandValue.NOTHING;
    }

    public HandValue straight(args args) {
        Vector<Integer> numbers = new Vector<Integer>();
        numbers = player.getHand().getNumbers();        

        for (int i = 0; i < numbers.size() - 5; i++) {
            if (numbers.get(i) + 1 == numbers.get(i + 1) && numbers.get(i + 1) + 1 == numbers.get(i + 2)
                    && numbers.get(i + 2) + 1 == numbers.get(i + 3) && numbers.get(i + 3) + 1 == numbers.get(i + 4)) {
                if (args.equals(ValueOfHand.args.Royal)) {
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

    private Card getHighCardFromDealer() {
        Card highCard = Dealer.getInstance().getHand().cards[0];
        for (Card card : Dealer.getInstance().getHand().cards) {
            if (card.getNumber() > highCard.getNumber()) {
                highCard = card;
            }
        }

        return highCard;
    }

    /** Returns true if player has higher card */
    public boolean getHighCard() {
        System.out.println("Player High Card: " + player.getHand().getHighCardFromPlayer());
        System.out.println("Dealer High Card: " + Dealer.getInstance().getHand().getHighCardFromPlayer());
        System.out.println("Dealer Cards: " + Dealer.getInstance().getNumbersFromDealer());
        if (
        Hand.compareCards(player.getHand().getNumbers().lastElement(),
                Dealer.getInstance().getHand().getNumbers().lastElement())) {
            // TODO: Get high card from player
                    return true;
                } else {
                    return false;
                }
        // return Hand.compareCards(Hand.compareCards(player.getHand().cards), Hand.compareCards(Dealer.getInstance().getHand().cards));
    }

    public int getValue() {
        return value;
    }

    public Vector<HandValue> getHandStats() {
        return currValue;
    }
}
