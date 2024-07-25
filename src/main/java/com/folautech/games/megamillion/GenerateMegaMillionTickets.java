package com.folautech.games.megamillion;

import com.folautech.games.powerball.PowerballResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateMegaMillionTickets {

    private static final int    MAX_WHITE_BALL   = 70;
    private static final int    MAX_RED_BALL     = 25;
    private static final int    WHITE_BALL_COUNT = 5;

    private static final double GRAND_PRIZE      = 10000000.0;

    private static List<Integer> historyWhiteBalls = new ArrayList<>();

    private static List<Integer> generatedWhiteBalls = new ArrayList<>();
    private static List<Integer> historyMegaBalls = new ArrayList<>();

    private static List<Integer> generatedMegaBalls = new ArrayList<>();

    private static boolean useHistoryData = false;

    private static int numberOfTickets = 0;

    public static void main(String[] args) {

        List<MegaMillionResult> megaMillionResults = new ArrayList<>();

        Map<Integer, Integer> megaballCounts = new HashMap<>();
        Map<Integer, Integer> ballCounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("mega_winning_numbers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

//                System.out.println("parts: "+Arrays.toString(parts));


                // Assuming the first 5 numbers are balls, the 6th is the powerball, and the 7th is the date
                List<Integer> balls = new ArrayList<>();

                MegaMillionResult megaMillionResult = new MegaMillionResult();
                megaMillionResult.setN1(Integer.parseInt(parts[0].trim()));
                megaMillionResult.setN2(Integer.parseInt(parts[1].trim()));
                megaMillionResult.setN3(Integer.parseInt(parts[2].trim()));
                megaMillionResult.setN4(Integer.parseInt(parts[3].trim()));
                megaMillionResult.setN5(Integer.parseInt(parts[4].trim()));

                int megaBall = Integer.parseInt(parts[5].trim());

                megaMillionResult.setMBall(megaBall);

                megaMillionResults.add(megaMillionResult);

                Integer megaballCount = megaballCounts.get(megaBall);

                if (megaballCount != null) {
                    megaballCounts.put(megaBall, megaballCount + 1);
                } else {
                    megaballCounts.put(megaBall, 1);
                }

                balls = Arrays.asList(megaMillionResult.getN1(),megaMillionResult.getN2(),
                        megaMillionResult.getN3(),megaMillionResult.getN4(),megaMillionResult.getN5());

                for (Integer ball : balls) {
                    Integer ballCount = ballCounts.get(ball);
                    if (ballCount != null) {
                        ballCounts.put(ball, ballCount + 1);
                    } else {
                        ballCounts.put(ball, 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ballCounts.entrySet().stream()
//        .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) // Sort by value, descending
//        .forEach(entry -> System.out.println("Ball: " + entry.getKey() + ", Count: " + entry.getValue()));

//        megaballCounts.entrySet().stream()
//                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) // Sort by value, descending
//                .forEach(entry -> System.out.println("Megaball: " + entry.getKey() + ", Count: " + entry.getValue()));



        historyWhiteBalls = ballCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) // Sort by value, descending
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

//        System.out.println("historyWhiteBalls: "+historyWhiteBalls);


        historyMegaBalls = megaballCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


//        System.out.println("historyMegaBalls: "+historyMegaBalls);

        Scanner scanner = new Scanner(System.in);;

        System.out.println("How many tickets?");
        numberOfTickets = Integer.parseInt(scanner.nextLine().trim());

        System.out.println("Do you want to use history data? (y/n)");
        String historyData = scanner.nextLine().trim().toLowerCase();

        useHistoryData = historyData != null && historyData.equalsIgnoreCase("y");

        int count = 1;

        while (numberOfTickets >= count) {

            List<Integer> whiteBalls = generateWhiteBalls();
            int megaBall = generateMegaBall();

            System.out.println(count+". "+whiteBalls.toString()+" - "+megaBall);

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

                nextBall = historyWhiteBalls.get(rand.nextInt(0, historyWhiteBalls.size()));

            }else{
                nextBall = rand.nextInt(1, MAX_WHITE_BALL+1);
            }

            if(!whiteBalls.contains(nextBall) && !generatedWhiteBalls.contains(nextBall)){
                whiteBalls.add(nextBall);
                generatedWhiteBalls.add(nextBall);
            }
        }

        return whiteBalls;
    }

    private static int generateMegaBall() {
        Random rand = new Random();

        while(true){
            int megaBall = 0;
            if(useHistoryData){

                if(numberOfTickets > 0 && numberOfTickets <= 5) {
                    megaBall = historyMegaBalls.get(rand.nextInt(0, numberOfTickets + 10));

                }else if(numberOfTickets > 5 && numberOfTickets <= 25){
                    megaBall = historyMegaBalls.get(rand.nextInt(0, MAX_RED_BALL));
                }else{
                    megaBall = historyMegaBalls.get(rand.nextInt(0, MAX_RED_BALL));
                }

            }else{
                megaBall = rand.nextInt(1, MAX_RED_BALL+1);
            }

            if(!generatedMegaBalls.contains(megaBall)){
                generatedMegaBalls.add(megaBall);
                return megaBall;
            }

//            return powerBall;
        }
    }

}
