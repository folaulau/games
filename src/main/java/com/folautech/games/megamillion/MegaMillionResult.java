package com.folautech.games.megamillion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MegaMillionResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("PlayDate")
    private Date PlayDate;

    @JsonProperty("N1")
    private int N1;

    @JsonProperty("N2")
    private int N2;

    @JsonProperty("N3")
    private int N3;

    @JsonProperty("N4")
    private int N4;

    @JsonProperty("N5")
    private int N5;

    @JsonProperty("MBall")
    private int MBall;

    @JsonProperty("Megaplier")
    private int Megaplier;

    @JsonProperty("UpdatedBy")
    private String UpdatedBy;

    @JsonProperty("UpdatedTime")
    private Date UpdatedTime;

    @JsonProperty("PlayDateTicks")
    private long PlayDateTicks;

    @JsonProperty("IgnoreServiceUntil")
    private Date IgnoreServiceUntil;

    public String getObjectAsString(){
        String result = Arrays.asList(N1,N2,N3,N4,N5).stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        result += (","+MBall);

        result += (","+PlayDate.toString());

        return result;
    }
}
