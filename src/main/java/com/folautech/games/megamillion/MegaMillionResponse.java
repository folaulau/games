package com.folautech.games.megamillion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Builder
//@NoArgsConstructor
@AllArgsConstructor
@Data
public class MegaMillionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, MegaMillionResults> d;

    public Map<String, MegaMillionResults> getD() {
        return d;
    }

    public void setD(Map<String, MegaMillionResults> d) {
        this.d = d;
    }
}
