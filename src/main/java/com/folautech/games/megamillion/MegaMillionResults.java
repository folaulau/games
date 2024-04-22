package com.folautech.games.megamillion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Builder
//@NoArgsConstructor
@AllArgsConstructor
@Data
public class MegaMillionResults implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("DrawingData")
    private List<MegaMillionResult> DrawingData;

    @JsonProperty("TotalResults")
    private int totalResults;

    public MegaMillionResults(){}

    public List<MegaMillionResult> getDrawingData() {
        return DrawingData;
    }

    public void setDrawingData(List<MegaMillionResult> drawingData) {
        DrawingData = drawingData;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
