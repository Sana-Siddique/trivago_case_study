package com.trivago.casestudy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advertiser {

    private String name;
    private int id;
    private List<Accommodation> accommodation;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Accommodation {
        private int id;
        private List<Price> prices;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Price {
        private String currency;
        private String price;
    }
}
