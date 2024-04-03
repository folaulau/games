package com.folautech.games.powerball;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PowerballResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private int powerball;

    private List<Integer> balls;

    private LocalDate date;

    public int getPowerball() {
        return powerball;
    }

    public void setPowerball(int powerball) {
        this.powerball = powerball;
    }

    public List<Integer> getBalls() {
        return balls;
    }

    public void setBalls(List<Integer> balls) {
        this.balls = balls;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void addBall(int ball){
        if(this.balls == null){
            this.balls = new ArrayList<>();
        }
        this.balls.add(ball);
    }

    public String getObjectAsString(){
        String result = balls.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));

        result += (","+powerball);

        result += (","+date.toString());

        return result;
    }
}
