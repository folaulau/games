package com.folautech.games.powerball;

import java.util.*;
import java.util.function.DoubleToLongFunction;

public class PowerballAutoMain {

    private static final int    MAX_WHITE_BALL   = 69;
    private static final int    MAX_RED_BALL     = 26;
    private static final int    WHITE_BALL_COUNT = 5;

    private static final double GRAND_PRIZE      = 10000000.0;

    public static void main(String[] args) {
        int count = 1;

        while (true) {
            Set<Integer> winningWhiteBalls = generateWhiteBalls();
            int winningRedBall = generateRedBall();

            Set<Integer> whiteBalls = generateWhiteBalls();
            int redBall = generateRedBall();

            System.out.println("==================================");
            System.out.println("Your Powerball numbers are:");
            for (int number : whiteBalls) {
                System.out.print(number + " ");
            }
            System.out.println("\nPowerball (red ball): " + redBall);

            System.out.println("==================================");

            Map<Integer, Boolean> yourWinningBalls = new HashMap<>();

            System.out.println("Winning Powerball numbers are:");
            for (Integer number : winningWhiteBalls) {
                System.out.print(number + " ");

                Optional<Boolean> winningWhiteBall = whiteBalls.stream().filter(wb -> wb.equals(number)).map(Objects::nonNull).findFirst();
                yourWinningBalls.put(number, winningWhiteBall.orElse(false));
            }

            System.out.println("\nWinning RedBall (red ball): " + winningRedBall);

            System.out.println("==================================");

            System.out.println("Your Powerball Results :");

            for (int number : yourWinningBalls.keySet()) {

                System.out.println("Ball " + number + ", win=" + yourWinningBalls.get(number));
            }
            System.out.println("RedBall: " + redBall + ", win=" + (winningRedBall == redBall));

            System.out.println("==================================");

            double yourPrize = getPrize(yourWinningBalls, (winningRedBall == redBall));

            System.out.println("\nYour Prize = $" + yourPrize + ", try=" + count);

            System.out.println("\nCongrats!!!");

            System.out.println("==================================");

            if (yourPrize == GRAND_PRIZE) {
                break;
            }

            count++;
        }

        // System.out.println("\nThanks for playing!");
    }

    private static Set<Integer> getWhiteBallsManually(Scanner scanner) {
        Set<Integer> whiteBalls = new HashSet<>();

        System.out.println("Enter 5 unique numbers between 1 and 69 for the white balls:");
        while (whiteBalls.size() < WHITE_BALL_COUNT) {
            try {
                int ball = scanner.nextInt();
                if (ball < 1 || ball > MAX_WHITE_BALL || whiteBalls.contains(ball)) {
                    System.out.println("Invalid number. Please enter a unique number between 1 and 69.");
                } else {
                    whiteBalls.add(ball);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 69.");
                scanner.next(); // clear invalid input
            }
        }

        return whiteBalls;
    }

    private static int getRedBallManually(Scanner scanner) {
        System.out.println("Enter a number between 1 and 26 for the red ball:");
        while (true) {
            try {
                int ball = scanner.nextInt();
                if (ball >= 1 && ball <= MAX_RED_BALL) {
                    return ball;
                }
                System.out.println("Invalid number. Please enter a number between 1 and 26.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 26.");
                scanner.next(); // clear invalid input
            }
        }
    }

    private static Set<Integer> generateWhiteBalls() {
        Set<Integer> whiteBalls = new HashSet<>();
        Random rand = new Random();

        while (whiteBalls.size() < WHITE_BALL_COUNT) {
            int nextBall = rand.nextInt(MAX_WHITE_BALL) + 1;
            whiteBalls.add(nextBall);
        }

        return whiteBalls;
    }

    private static int generateRedBall() {
        Random rand = new Random();
        return rand.nextInt(MAX_RED_BALL) + 1;
    }

    private static Double getPrize(Map<Integer, Boolean> yourWinningBalls, boolean redBall) {
        if (yourWinningBalls == null || yourWinningBalls.keySet().isEmpty()) {
            if (redBall) {
                return 4.0;
            } else {
                return 0.0;
            }
        }

        if (redBall && yourWinningBalls.values().stream().filter(b -> b.booleanValue()).count() == 5) {
            return GRAND_PRIZE;
        }

        if (redBall == false && yourWinningBalls.values().stream().filter(b -> b.booleanValue()).count() == 5) {
            return 1000000.0;
        }

        if (redBall && yourWinningBalls.values().stream().filter(b -> b.booleanValue()).count() == 4) {
            return 50000.0;
        }

        if (redBall == false && yourWinningBalls.values().stream().filter(b -> b.booleanValue()).count() == 4) {
            return 100.0;
        }

        if (redBall && yourWinningBalls.values().stream().filter(b -> b.booleanValue()).count() == 3) {
            return 1000.0;
        }

        if (redBall == false && yourWinningBalls.values().stream().filter(b -> b.booleanValue()).count() == 3) {
            return 7.0;
        }

        if (redBall && yourWinningBalls.values().stream().filter(b -> b.booleanValue()).count() == 2) {
            return 7.0;
        }

        if (redBall && yourWinningBalls.values().stream().filter(b -> b.booleanValue()).count() == 1) {
            return 4.0;
        }

        if (redBall) {
            return 4.0;
        }

        return 0.0;
    }
}
