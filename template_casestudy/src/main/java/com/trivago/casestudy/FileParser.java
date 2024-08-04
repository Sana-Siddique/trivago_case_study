package com.trivago.casestudy;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileParser {

    public List<Advertiser> parseJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Advertiser> priceList = null;
        File file = new File(filePath);
         priceList = List.of(objectMapper.readValue(new File(filePath), Advertiser.class));
         return priceList;
    }

}
