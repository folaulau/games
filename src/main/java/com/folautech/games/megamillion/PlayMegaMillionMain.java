package com.folautech.games.megamillion;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
public class PlayMegaMillionMain {
    private static final int WHITE_BALLS_COUNT = 5;
    private static final int MAX_WHITE_BALL = 70;
    private static final int MAX_MEGA_BALL = 25;

    public static void main(String[] args) {
        Set<Integer> winningWhiteBalls = pickWhiteBalls();
        int winningMegaBall = pickMegaBall();

        System.out.println("Winning numbers are: " + winningWhiteBalls + " and the Mega Ball is: " + winningMegaBall);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your " + WHITE_BALLS_COUNT + " white ball numbers (between 1 and " + MAX_WHITE_BALL + "):");
        Set<Integer> userWhiteBalls = new HashSet<>();
        while (userWhiteBalls.size() < WHITE_BALLS_COUNT) {
            int number = scanner.nextInt();
            if (number >= 1 && number <= MAX_WHITE_BALL && !userWhiteBalls.contains(number)) {
                userWhiteBalls.add(number);
            } else {
                System.out.println("Invalid number, try again.");
            }
        }

        System.out.println("Enter your Mega Ball number (between 1 and " + MAX_MEGA_BALL + "):");
        int userMegaBall = scanner.nextInt();
        while (userMegaBall < 1 || userMegaBall > MAX_MEGA_BALL) {
            System.out.println("Invalid number, try again.");
            userMegaBall = scanner.nextInt();
        }
        scanner.close();

        System.out.println("Your numbers are: " + userWhiteBalls + " and your Mega Ball is: " + userMegaBall);

        checkForWinner(winningWhiteBalls, winningMegaBall, userWhiteBalls, userMegaBall);
    }

    private static Set<Integer> pickWhiteBalls() {
        Random rand = new Random();
        Set<Integer> whiteBalls = new HashSet<>();
        while (whiteBalls.size() < WHITE_BALLS_COUNT) {
            whiteBalls.add(rand.nextInt(MAX_WHITE_BALL) + 1);
        }
        return whiteBalls;
    }

    private static int pickMegaBall() {
        Random rand = new Random();
        return rand.nextInt(MAX_MEGA_BALL) + 1;
    }

    private static void checkForWinner(Set<Integer> winningWhiteBalls, int winningMegaBall, Set<Integer> userWhiteBalls, int userMegaBall) {
        if (winningWhiteBalls.equals(userWhiteBalls) && winningMegaBall == userMegaBall) {
            System.out.println("Congratulations! You won the jackpot!");
        } else if (winningWhiteBalls.equals(userWhiteBalls)) {
            System.out.println("Congratulations! You matched all the white balls!");
        } else {
            System.out.println("Sorry, you did not win this time. Better luck next time!");
        }
    }
}
