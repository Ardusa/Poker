package Poker;

import java.io.Console;
import java.util.Scanner;

public class Player {
    private Console console;
    private Scanner scanner;
    private Hand hand;
    private int money;
    private String name;
    public boolean folded;
    private String password;

    public Player(String name) {
        this.name = name;
        hand = new Hand();
        money = 1000;
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

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public double requestBet() {
        double betValue;
        System.out.print("How much would you like to bet: $");
        betValue = scanner.nextDouble();
        if (betValue > money) {
            System.out.println("You don't have that much money!\n You have $" + money + "\n");
            betValue = requestBet();
        } else if (betValue < 0) {
            System.out.println("You can't bet negative money!\n");
            betValue = requestBet();
        } else {
            money -= betValue;
        }
        return betValue;
    }

    public double call(double callValue) {
        money -= callValue;
        return callValue;
    }

    public int folded() {
        if (folded) {
            return 1;
        } else {
            return 0;
        }
    }

    public void showCards() {
        System.out.println();
        System.out.println("Your cards are: ");
        System.out.println(hand.cards[0].toString());
        System.out.println(hand.cards[1].toString());
        System.out.println();
    }

    public void showMoney() {
        System.out.println(name + ", you have $" + money);
    }

    /** This is to create a password and will overwrite the current password if called */
    public void setPassword() {
        char[] password = null;
        try {
            password = console.readPassword("Enter Password: ");
        } catch (Exception e) {
            System.out.println("Invalid Input");
            setPassword();
        }
        this.password= parsePassword(password);
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

    /** Use this most of the time for passwords, will get, check and repeat function until correct password is given */
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
        // System.out.println("Entered pw: " + pwInput + "\nActual pw: " + this.password);
        if (!this.password.equals(pwInput)) {
            System.out.println("Incorrect Password");
            getAndCheckPW();
        }
    }

    /** True if password matches, false if password is wrong 
     * @deprecated Use getAndCheckPW() instead
    */
    public boolean checkPassword(char[] password) {
        // System.out.print(password.equals(password));
        return (this.password.equals(parsePassword(password)));
    }

    public void printPassword() {
        System.out.println(password);
    }

    /** Returns string of password */
    private String parsePassword(char[] pw) {
        String pass = "";
        for (char character : pw) {
            pass += character;
        }
        return pass;
    }
}
