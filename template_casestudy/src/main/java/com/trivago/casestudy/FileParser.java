package com.trivago.casestudy;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trivago.casestudy.exceptionHandler.FileParsingException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileParser {

    private final ObjectMapper objectMapper;

    public FileParser() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Advertiser> parseJsonFile(String filePath) throws FileParsingException {
        File file = new File(filePath);
        List<Advertiser> priceList = null;

        try {
            priceList = List.of(objectMapper.readValue(new File(filePath), Advertiser.class));
        } catch (JsonMappingException e) {
            throw new FileParsingException("Error mapping JSON to Price objects", e);
        } catch (IOException e) {
            throw new FileParsingException("I/O error reading JSON file", e);
        }
        return priceList;
    }


    public List<Advertiser> parseYamlFile(String filePath) throws FileParsingException {
        Yaml yaml = new Yaml();
        List<Advertiser> advertisers = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            Iterable<Object> yamlData = yaml.loadAll(reader);
            for (Object data : yamlData) {
                if (data instanceof Map) {
                    Map<String, Object> advertiserData = (Map<String, Object>) data;
                    String name = (String) advertiserData.get("name");
                    int id = (int) advertiserData.get("id");
                    List<Advertiser.Accommodation> accommodations = new ArrayList<>();
                    List<Map<String, Object>> accommodationsData = (List<Map<String, Object>>) advertiserData.get("accommodation");
                    for (Map<String, Object> accommodationData : accommodationsData) {
                        int accommodationId = (int) accommodationData.get("id");
                        List<Advertiser.Price> prices = new ArrayList<>();
                        List<Map<String, Object>> pricesData = (List<Map<String, Object>>) accommodationData.get("prices");
                        for (Map<String, Object> priceData : pricesData) {
                            String currency = (String) priceData.get("currency");
                            String price = String.valueOf(priceData.get("price"));
                            prices.add(new Advertiser.Price(currency, price));
                        }
                        accommodations.add(new Advertiser.Accommodation(accommodationId, prices));
                    }
                    advertisers.add(new Advertiser(name, id, accommodations));
                }
            }
        } catch (IOException e) {
            throw new FileParsingException("I/O error reading YAML file", e);
        } catch (Exception e) {
            throw new FileParsingException("Error mapping YAML to Advertiser objects", e);
        }
        return advertisers;
    }

}
