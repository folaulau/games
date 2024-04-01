package com.folautech.games.powerball;

import com.folautech.games.utils.HttpRequestInterceptor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PowerballRestService {

    public static void main(String[] args) {
        PowerballRestService powerballRestService = new PowerballRestService();
        int perPage = 30;
        int perWeek = 3;
        int perMonth = perWeek * 4;
        int perYr = (perMonth * 12) /perPage;
        int numOfPages = (int)(perYr * 5);
        List<PowerballResult> powerballResults = powerballRestService.getPreviousResults(numOfPages);

        System.out.println("Size: "+powerballResults.size());

        System.out.println("From: "+powerballResults.get(powerballResults.size()-1).getDate().toString() +" to "+powerballResults.get(0).getDate().toString() );

//        for(PowerballResult powerballResult: powerballResults){
//            System.out.println("Powerball: "+powerballResult.getPowerball()+", balls: "+powerballResult.getBalls());
//
//        }

        Map<Integer, Integer> powerballCounts = new HashMap<>();
        Map<Integer, Integer> ballCounts = new HashMap<>();

        for(PowerballResult powerballResult: powerballResults){

            int powerball = powerballResult.getPowerball();
            Integer powerballCount = powerballCounts.get(powerball);

            if(powerballCount!=null){
                powerballCounts.put(powerball, powerballCount+1);
            }else{
                powerballCounts.put(powerball,1);
            }

            List<Integer> balls = powerballResult.getBalls();

            for(Integer ball : balls){
                Integer ballCount = ballCounts.get(ball);
                if(ballCount != null){
                    ballCounts.put(ball, ballCount+1);
                }else{
                    ballCounts.put(ball,1);
                }

            }
        }

//        for(Integer powerball : powerballCounts.keySet()){
//            System.out.println("Powerball: " + powerball+ ", Count: " + powerballCounts.get(powerball));
//        }

        powerballCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) // Sort by value, descending
                .forEach(entry -> System.out.println("Powerball: " + entry.getKey() + ", Count: " + entry.getValue()));

        ballCounts.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) // Sort by value, descending
                .forEach(entry -> System.out.println("Ball: " + entry.getKey() + ", Count: " + entry.getValue()));



//        ballCounts.entrySet().stream()
//                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed()) // Sort by value, descending
//                .forEach(entry -> System.out.println("Ball: " + entry.getKey() + ", Count: " + entry.getValue()));
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", Locale.ENGLISH);

    public PowerballRestService(){
    }


    public List<PowerballResult> getPreviousResults(int numberOfPages){

        Document docCustomConn = null;

        List<PowerballResult> results = new ArrayList<>();

        int pageNumber = 1;

        System.out.println("loading results...");

        while (numberOfPages >= 0) {

            StringBuilder url = new StringBuilder("https://www.powerball.com/previous-results");
            url.append("?gc=powerball");
            url.append("&pg="+pageNumber);

            try {
//            Document doc = Jsoup.connect(url.toString()).get();

                Connection connection = Jsoup.connect(url.toString());
                connection.userAgent("Mozilla");
                connection.timeout(5000);
                connection.cookie("cookiename", "val234");
                connection.cookie("cookiename", "val234");
                connection.referrer("http://google.com");
                connection.header("headersecurity", "xyz123");
                docCustomConn = connection.get();
            } catch (IOException e) {
               System.err.println("IOException, msg: "+e.getLocalizedMessage());
               break;
            }

            if (docCustomConn == null) {
                break;
            }

            Elements dateElements = docCustomConn.select("h5.card-title");

            int count = 0;
            for (Element dateElement : dateElements) {
//                System.out.println("Date: " + dateElement.text());

                PowerballResult powerballResult = new PowerballResult();

                LocalDate date = LocalDate.parse(dateElement.text(), formatter);
//                System.out.println("LocalDate: " + date);

                powerballResult.setDate(date);

                Element parentCard = dateElement.closest(".card");
                if (parentCard != null) {
                    Elements powerballNumbers = parentCard.select(".item-powerball:not(.powerball)");
                    Elements powerball = parentCard.select(".powerball.item-powerball");

                    powerballResult.setPowerball(Integer.parseInt(powerball.text()));

//                    System.out.print("Powerball Numbers: ");
                    for (Element number : powerballNumbers) {
//                        System.out.print(number.text() + " ");

                        powerballResult.addBall(Integer.parseInt(number.text()));

                    }

//                    System.out.println("\nPowerball: " + powerball.text());
                }
//                System.out.println(); // Just to add a space between different dates

                results.add(powerballResult);

                count++;


            }

//            System.out.println("count per page: "+count);

            numberOfPages--;
            pageNumber++;


        }

        System.out.println("done loading results");

        return results;
    }
}
