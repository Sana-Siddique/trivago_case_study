package com.trivago.casestudy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Advertiser {

    private String name;
    private int id;
    private List<Accommodation> accommodation;

    @Data
    @AllArgsConstructor
    public static class Accommodation {
        private int id;
        private List<Price> prices;
    }

    @Data
    @AllArgsConstructor
    public static class Price {
        private String currency;
        private String price;
    }
}
