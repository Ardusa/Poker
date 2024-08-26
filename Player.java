import java.io.Console;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class Player {
    private Console console;
    private Scanner scanner;

    private Hand hand;
    public Round.blind blind;

    public double money, personalPotValue;
    private String name, password;
    public boolean allIn, called, folded;

    private int wins;

    public Player(String name) {
        this.name = name;
        hand = new Hand();

        for (Card card : hand.cards) {
            card.setPlayerHash(this.hashCode());
        }
        money = Constants.startingMoney;
        scanner = new Scanner(System.in);
        console = System.console();
    }

    @Override
    public String toString() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public double requestBet() {
        double betValue;
        System.out.print("How much would you like to bet: $");
        betValue = scanner.nextDouble();
        if (betValue > money) {
            System.out.println("You don't have that much money!");
            betValue = requestBet();
        } else if (betValue < 0) {
            System.out.println("You can't bet negative money!\n");
            betValue = requestBet();
        } else {
            money -= betValue;
        }
        personalPotValue += betValue;
        if (money == 0) {
            allIn();
        }
        return betValue;
    }

    public double call(double callValue) {
        double returnVal = callValue - personalPotValue;
        money -= returnVal;
        personalPotValue = callValue;
        if (money == 0) {
            allIn();
        }
        return returnVal;
    }

    public int folded() {
        if (folded) {
            return 1;
        } else {
            return 0;
        }
    }

    /** Will show the full hand, Only use for testing and showdown */
    public void showCards() {
        System.out.println(this.name + "'s Cards:");
        for (Card card : hand.cards) {
            System.out.println(card.toString());
        }
    }

    public void showMoney() {
        System.out.println(name + ", you have $" + money);
    }

    /**
     *             This is to create a password and will overwrite the current
     *             password if called
     */
    public void setPassword() {
        char[] password = null;
        try {
            password = console.readPassword("Enter Password: ");
            if (password.equals("")) {
                throw new InvalidParameterException(name + " tried to set a blank password");
            }
        } catch (Exception e) {
            System.out.println("Invalid Input");
            setPassword();
        }
        this.password = parsePassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @deprecated Use getAndCheckPW() instead
     */
    public String getPassword() {
        String pw = null;
        try {
            pw = parsePassword(console.readPassword("Enter Password: "));
        } catch (Exception e) {
            System.out.println("Invalid Input");
            pw = getPassword();
        }
        return pw;
    }

    /**
     * Use this most of the time for passwords, will get, check and repeat function
     * until correct password is given
     */
    public void getAndCheckPW() {
        String pwInput;
        char[] pw = null;
        try {
            pw = console.readPassword("Enter Password: ");
        } catch (Exception e) {
            System.out.println("Invalid Input");
            getAndCheckPW();
        }
        pwInput = parsePassword(pw);
        if (!this.password.equals(pwInput)) {
            System.out.println("Incorrect Password");
            getAndCheckPW();
        }
    }

    /**
     * True if password matches, false if password is wrong
     * 
     * @deprecated Use getAndCheckPW() instead
     */
    public boolean checkPassword(char[] password) {
        return (this.password.equals(parsePassword(password)));
    }

    public void printPassword() {
        System.out.println(password);
    }

    /** Returns string of password */
    private String parsePassword(char[] password) {
        String pass = "";
        for (char character : password) {
            pass += character;
        }
        return pass;
    }

    public void win(double pot) {
        wins++;
        money += pot;
    }

    public int getWins() {
        return wins;
    }

    public void setBlind(Round.blind blind) {
        this.blind = blind;
    }

    public Round.blind getBlind() {
        return blind;
    }

    public void allIn() {
        allIn = true;
        System.out.println("You are all in!");
    }

    public boolean isAllIn() {
        return allIn;
    }

    public void printCycleStats() {
        System.out.println("You have $" + money);
        System.out.println("You have bet $" + personalPotValue + " this round");
    }

    public ValueOfHand getHandValue() {
        return new ValueOfHand(this);
    }
}
