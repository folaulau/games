package com.folautech.games.powerball;

import java.util.*;

public class GenerateNumbers {
    private static final int MAX_WHITE_BALL = 69;
    private static final int MAX_RED_BALL = 26;
    private static final int WHITE_BALL_COUNT = 5;

    public static void main(String[] args) {
        System.out.println("=== Powerball Numbers for Tomorrow's Drawing ===\n");
        
        for (int i = 1; i <= 5; i++) {
            Set<Integer> whiteBalls = generateWhiteBalls();
            int redBall = generateRedBall();
            
            System.out.printf("Set %d: ", i);
            
            // Convert to list and sort for display
            List<Integer> sortedWhiteBalls = new ArrayList<>(whiteBalls);
            Collections.sort(sortedWhiteBalls);
            
            for (int j = 0; j < sortedWhiteBalls.size(); j++) {
                System.out.printf("%02d", sortedWhiteBalls.get(j));
                if (j < sortedWhiteBalls.size() - 1) {
                    System.out.print(" ");
                }
            }
            
            System.out.printf(" | Powerball: %02d\n", redBall);
        }
        
        System.out.println("\nGood luck!");
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
}