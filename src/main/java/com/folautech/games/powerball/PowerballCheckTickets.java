package com.folautech.games.powerball;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PowerballCheckTickets {

    private static final double GRAND_PRIZE      = 140000000.0;

    public static void main(String[] args) {

        List<PowerballResult> yourPowerBallTickets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("powerball_ticket_numbers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Assuming the first 5 numbers are balls, the 6th is the powerball, and the 7th is the date
                List<Integer> balls = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    balls.add(Integer.parseInt(parts[i].trim()));
                }
                int powerball = Integer.parseInt(parts[5].trim());

                PowerballResult powerballResult = new PowerballResult();
                powerballResult.setPowerball(powerball);
                powerballResult.setBalls(balls);

                yourPowerBallTickets.add(powerballResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the drawer ticket like this(18, 19, 25, 40, 64, 14) with powerball being the last number:");
        String drawwerPowerBallTicket = scanner.nextLine().trim().toLowerCase();

        String[] parts = drawwerPowerBallTicket.split(",");
        // Assuming the first 5 numbers are balls, the 6th is the powerball, and the 7th is the date
        List<Integer> balls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            balls.add(Integer.parseInt(parts[i].trim()));
        }
        int powerball = Integer.parseInt(parts[5].trim());

        PowerballResult drawwerPowerballResult = new PowerballResult();
        drawwerPowerballResult.setPowerball(powerball);
        drawwerPowerballResult.setBalls(balls);

        double total = 0;

        for (PowerballResult yourPowerballTicket : yourPowerBallTickets){
            System.out.println(yourPowerballTicket);

            double yourPrize = getPrize(drawwerPowerballResult, yourPowerballTicket);

            total += yourPrize;

            System.out.println("Your Prize = $" + yourPrize+"\n");
        }

        System.out.println("\nYour Total Prize = $" + total);

    }

    private static Double getPrize(PowerballResult drawwerPowerballResult, PowerballResult ticket) {

        boolean powerBallWinner = drawwerPowerballResult.getPowerball() == ticket.getPowerball();

        List<Integer> drawwerBalls = drawwerPowerballResult.getBalls();

        int ticketWinningBalls = 0;

        for(Integer ball : drawwerBalls){

            if(ticket.getBalls().contains(ball)){
                ticketWinningBalls ++;
            }

        }

        System.out.println("powerBallWinner: "+powerBallWinner+", number of Winning Balls: "+ticketWinningBalls);


        if (powerBallWinner && ticketWinningBalls == 5) {
            return GRAND_PRIZE;
        }
        // $1M
        if (powerBallWinner == false && ticketWinningBalls == 5) {
            return 1000000.0;
        }

        // $50K
        if (powerBallWinner && ticketWinningBalls == 4) {
            return 50000.0;
        }

        // $100
        if (powerBallWinner == false && ticketWinningBalls == 4) {
            return 100.0;
        }

        // $100
        if (powerBallWinner && ticketWinningBalls == 3) {
            return 100.0;
        }

        if (powerBallWinner == false && ticketWinningBalls == 3) {
            return 7.0;
        }

        if (powerBallWinner && ticketWinningBalls == 2) {
            return 7.0;
        }

        if (powerBallWinner && ticketWinningBalls == 1) {
            return 4.0;
        }

        if (powerBallWinner) {
            return 4.0;
        }

        return 0.0;
    }

}
