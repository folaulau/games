package com.folautech.games.megamillion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.folautech.games.powerball.PowerballResult;
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MegaMillionRestService {


    private ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        MegaMillionRestService megaMillionRestService = new MegaMillionRestService();
        int year = 4;
        int numOfPages = year * 12;
        List<MegaMillionResult> megaMillionResults = megaMillionRestService.getPreviousResults(numOfPages);

        System.out.println("Number of Drawings: " + megaMillionResults.size());

        System.out.println("\n\nFrom: " + megaMillionResults.get(megaMillionResults.size() - 1).getPlayDate().toString() + " to "
                + megaMillionResults.get(0).getPlayDate().toString());


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("mega_winning_numbers.txt"))) {
            for (int i = 0; i < megaMillionResults.size(); i++) {
                MegaMillionResult megaMillionResult = megaMillionResults.get(i);
                writer.write(megaMillionResult.getObjectAsString());
                writer.newLine(); // Adds a newline character
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("done loading results");
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", Locale.ENGLISH);

    public MegaMillionRestService() {
    }


    public List<MegaMillionResult> getPreviousResults(int numberOfPages) {

        List<MegaMillionResult> results = new ArrayList<>();

        System.out.println("loading results for " + numberOfPages + " pages...");

        final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);

        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
        restTemplate.getInterceptors().add(new HttpRequestInterceptor());


        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        int count = 1;

        while (numberOfPages >= count) {

            StringBuilder url = new StringBuilder("https://www.megamillions.com/cmspages/utilservice.asmx/GetDrawingPagingData");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Accept","*/*");
            headers.add("Accept-Encoding","*/*");

            HttpEntity<String> requestEntity = null;

            try {
                requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(Map.of("pageSize",25, "pageNumber",count,"startDate","","endDate","")), headers);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            MegaMillionResponse resultDto = null;
            try {
                ResponseEntity<Map<String, String>> response = restTemplate.exchange(url.toString(), HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, String>>() {
                });

                // Output results
                Map<String, String> bodyAsString = response.getBody();
                System.out.println("bodyAsString: "+bodyAsString);
//            log.info("bodyAsString={}", bodyAsString);
                try {
//                    resultDto = objectMapper.readValue(bodyAsString, MegaMillionResponse.class);

                    String d = bodyAsString.get("d");

                    System.out.println("resultDtos="+ d);

                    MegaMillionResults megaMillionResults = objectMapper.readValue(d, MegaMillionResults.class);

                    System.out.println("megaMillionResults="+ megaMillionResults);

                    System.out.println("From: " + megaMillionResults.getDrawingData().get(megaMillionResults.getDrawingData().size() - 1).getPlayDate().toString() + " to "
                            + megaMillionResults.getDrawingData().get(0).getPlayDate().toString());

                    results.addAll(megaMillionResults.getDrawingData());

                } catch (Exception e) {
                    System.out.println("Exception1, msg="+ e.getLocalizedMessage());
                }

            } catch (Exception e) {
                System.out.println("Exception2, msg="+ e.getLocalizedMessage());
            }

            count++;

        }

        System.out.println("done loading results");

        return results;
    }
}
