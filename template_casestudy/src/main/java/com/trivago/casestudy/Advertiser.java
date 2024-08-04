package com.trivago.casestudy;

import lombok.Data;

import java.util.List;

@Data
public class Advertiser {

    private String name;
    private int id;
    private List<Accommodation> accommodation;

    @Data
    public static class Accommodation {
        private int id;
        private List<Price> prices;
    }

    @Data
    public static class Price {
        private String currency;
        private String price;
    }
}
