package Poker;

import java.util.Scanner;

public class Player {
    private Scanner scanner;
    private Hand hand;
    private int money;
    private String name;
    public boolean folded;

    public Player(String name) {
        this.name = name;
        hand = new Hand();
        money = 1000;
        scanner = new Scanner(System.in);
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
}
