package com.folautech.games.powerball;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GeneratePowerballTickets {

    private static final int    MAX_WHITE_BALL   = 69;
    private static final int    MAX_RED_BALL     = 26;
    private static final int    WHITE_BALL_COUNT = 5;

    private static final double GRAND_PRIZE      = 10000000.0;

    private static List<Integer> historyWhiteBalls = new ArrayList<>();

    private static List<Integer> generatedWhiteBalls = new ArrayList<>();
    private static List<Integer> historyPowerBalls = new ArrayList<>();

    private static List<Integer> generatedPowerBalls = new ArrayList<>();

    private static boolean useHistoryData = false;

    private static int numberOfTickets = 0;

    public static void main(String[] args) {

        List<PowerballResult> powerballResults = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("powerball_winning_numbers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Assuming the first 5 numbers are balls, the 6th is the powerball, and the 7th is the date
                List<Integer> balls = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    balls.add(Integer.parseInt(parts[i].trim()));
                }
                int powerball = Integer.parseInt(parts[5].trim());
                LocalDate date = LocalDate.parse(parts[6].trim());

                PowerballResult powerballResult = new PowerballResult();
                powerballResult.setPowerball(powerball);
                powerballResult.setBalls(balls);
                powerballResult.setDate(date);

                powerballResults.add(powerballResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Integer, Integer> powerballCounts = new HashMap<>();
        Map<Integer, Integer> ballCounts = new HashMap<>();

        for (PowerballResult powerballResult : powerballResults) {

            int powerball = powerballResult.getPowerball();
            Integer powerballCount = powerballCounts.get(powerball);

            if (powerballCount != null) {
                powerballCounts.put(powerball, powerballCount + 1);
            } else {
                powerballCounts.put(powerball, 1);
            }

            List<Integer> balls = powerballResult.getBalls();

            for (Integer ball : balls) {
                Integer ballCount = ballCounts.get(ball);
                if (ballCount != null) {
                    ballCounts.put(ball, ballCount + 1);
                } else {
                    ballCounts.put(ball, 1);
                }

            }
        }

        //        ballCounts.entrySet().stream()
//                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) // Sort by value, descending
//                .forEach(entry -> System.out.println("Ball: " + entry.getKey() + ", Count: " + entry.getValue()));

//        powerballCounts.entrySet().stream()
//                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) // Sort by value, descending
//                .forEach(entry -> System.out.println("Powerball: " + entry.getKey() + ", Count: " + entry.getValue()));



        historyWhiteBalls = ballCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) // Sort by value, descending
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        historyPowerBalls = powerballCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        Scanner scanner = new Scanner(System.in);;

        System.out.println("How many tickets?");
        numberOfTickets = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Do you want to use history data? (y/n)");
        String historyData = scanner.nextLine().trim().toLowerCase();

        useHistoryData = historyData != null && historyData.equalsIgnoreCase("y");

        int count = 1;

        while (numberOfTickets >= count) {

            List<Integer> whiteBalls = generateWhiteBalls();
            int powerBall = generateRedBall();

            System.out.println(count+". "+whiteBalls.toString()+" - "+powerBall);

            count++;

        }

        System.out.println("\nGood luck!!!");
    }

    private static List<Integer> generateWhiteBalls() {
        List<Integer> whiteBalls = new ArrayList<>();
        Random rand = new Random();

        while (whiteBalls.size() < WHITE_BALL_COUNT) {
            int nextBall = 0;

            if(useHistoryData){

                nextBall = historyWhiteBalls.get(rand.nextInt(historyWhiteBalls.size()));

            }else{
                nextBall = rand.nextInt(MAX_WHITE_BALL) + 1;
            }

            if(!whiteBalls.contains(nextBall) && !generatedWhiteBalls.contains(nextBall)){
                whiteBalls.add(nextBall);
                generatedWhiteBalls.add(nextBall);
            }
        }

        return whiteBalls;
    }

    private static int generateRedBall() {
        Random rand = new Random();

        while(true){
            int powerBall = 0;
            if(useHistoryData){

                if(numberOfTickets > 0 && numberOfTickets <= 26){
                    powerBall = historyPowerBalls.get(rand.nextInt(numberOfTickets));
                }else{
                    powerBall = historyPowerBalls.get(rand.nextInt(MAX_RED_BALL));
                }

            }else{
                powerBall = rand.nextInt(MAX_RED_BALL)+1;
            }

            if(!generatedPowerBalls.contains(powerBall)){
                generatedPowerBalls.add(powerBall);
                return powerBall;
            }

//            return powerBall;
        }
    }

}
