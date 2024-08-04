package com.trivago.casestudy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advertiser implements Serializable {

    private String name;
    private int id;
    private List<Accommodation> accommodation;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Accommodation implements Serializable{
        private int id;
        private List<Price> prices;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Price implements Serializable{
        private String currency;
        private String price;
    }
}
