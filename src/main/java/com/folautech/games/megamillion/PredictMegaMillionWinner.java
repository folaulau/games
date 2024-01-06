package com.folautech.games.megamillion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PredictMegaMillionWinner {

    public static void main(String[] args) {
        String fileName = "prev_winning_numbers.txt"; // Ensure this file is in the correct directory


        Map<Integer, Integer> numbersCounter = new HashMap<>();
        Map<Integer, Integer> megaCount = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

//            List<List<Integer>> winningNumbers = new ArrayList<>();

            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                String[] row = line.split(",");
                if(row.length!=6){
                    System.err.println("Something is wrong: "+row);
                }

                for(int i=0;i<row.length;i++){
                    String numberAsStr = row[i];
                    Integer number = Integer.parseInt(numberAsStr);

                    if((i+1)==row.length){
                        megaCount.put(number, megaCount.getOrDefault(number, 0)+1);
                    }else{
                        numbersCounter.put(number, numbersCounter.getOrDefault(number, 0)+1);
                    }
                }

//                winningNumbers.add(Arrays.asList(row).stream().map(numberAsStr -> Integer.parseInt(numberAsStr)).toList());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for(Integer key : numbersCounter.keySet()){
//          Integer value =  numbersCounter.get(key);
//          System.out.println("key: "+key+", value: "+value);
//        }

        // Sorting numbersCounter by values in descending order
        LinkedHashMap<Integer, Integer> sortedNumbersCounter = numbersCounter.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

// Sorting megaCount by values in descending order
        LinkedHashMap<Integer, Integer> sortedMegaCount = megaCount.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

// Printing sorted maps
        System.out.println("Sorted Numbers Counter:");
        sortedNumbersCounter.forEach((key, value) -> System.out.println("Number: " + key + ", Count: " + value));

        System.out.println("\nSorted Mega Numbers Counter:");
        sortedMegaCount.forEach((key, value) -> System.out.println("Mega Number: " + key + ", Count: " + value));

    }

}
