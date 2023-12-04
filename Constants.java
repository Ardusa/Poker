import java.util.Vector;

public class Constants {
    public final static double startingMoney = 10 * 100;
    public final static double bigBlind = startingMoney / 100;
    public final static double smallBlind = bigBlind / 2;
    public final static int dealerCards = 5;

    public final static String blankLine = "----------------------------------------------------------------------";

    public class handValues {
        public final static int royalFlushValue = 10;
        public final static int straightFlushValue = 9;
        public final static int fourOfAKindValue = 8;
        public final static int fullHouseValue = 7;
        public final static int flushValue = 6;
        public final static int straightValue = 5;
        public final static int threeOfAKindValue = 4;
        public final static int twoPairValue = 3;
        public final static int pairValue = 2;
        public final static int highCardValue = 1;

        public final static Vector<Hand.HandValue> handsWithHighCard = new Vector<Hand.HandValue>();
    }

}
