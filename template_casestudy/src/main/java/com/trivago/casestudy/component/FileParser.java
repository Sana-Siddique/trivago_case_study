package com.trivago.casestudy.component;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trivago.casestudy.model.Advertiser;
import com.trivago.casestudy.exceptionHandler.FileParsingException;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FileParser {

    private final ObjectMapper objectMapper;
    private final Yaml yaml;


    //class constructor to initialize mappers for yaml and json
    public FileParser() {
        this.objectMapper = new ObjectMapper();
        this.yaml = new Yaml();
    }


    public List<Advertiser> parseJsonFile(String filePath) throws FileParsingException {
        File file = new File(filePath);
        List<Advertiser> priceList = null;

        try {
            priceList = List.of(objectMapper.readValue(new File(filePath), Advertiser.class));
        } catch (JsonMappingException e) {
            throw new FileParsingException("Error mapping JSON to Advertiser objects", e);
        } catch (IOException e) {
            throw new FileParsingException("I/O error reading JSON file", e);
        }
        return priceList;
    }


    public List<Advertiser> parseYamlFile(String filePath) throws FileParsingException {
        List<Advertiser> advertisers = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            Iterable<Object> yamlData = yaml.loadAll(reader);
            for (Object data : yamlData) {
                if (data instanceof Map<?, ?> advertiserData) {
                    advertisers.add(parseAdvertiser(advertiserData));
                }
            }
        } catch (IOException e) {
            throw new FileParsingException("I/O error reading YAML file", e);
        } catch (Exception e) {
            throw new FileParsingException("Error mapping YAML to Advertiser objects", e);
        }

        return advertisers;
    }

    private Advertiser parseAdvertiser(Map<?, ?> advertiserData) throws FileParsingException {
        try {
            String name = (String) advertiserData.get("name");
            int id = (int) advertiserData.get("id");
            List<Map<String, Object>> accommodationsData = (List<Map<String, Object>>) advertiserData.get("accommodation");
            List<Advertiser.Accommodation> accommodations = new ArrayList<>();

            for (Map<String, Object> accommodationData : accommodationsData) {
                accommodations.add(parseAccommodation(accommodationData));
            }

            return new Advertiser(name, id, accommodations);
        } catch (ClassCastException | NullPointerException e) {
            throw new FileParsingException("Error parsing Advertiser data", e);
        }
    }

    private Advertiser.Accommodation parseAccommodation(Map<String, Object> accommodationData) throws FileParsingException {
        try {
            int accommodationId = (int) accommodationData.get("id");
            List<Map<String, Object>> pricesData = (List<Map<String, Object>>) accommodationData.get("prices");
            List<Advertiser.Price> prices = new ArrayList<>();

            for (Map<String, Object> priceData : pricesData) {
                prices.add(parsePrice(priceData));
            }

            return new Advertiser.Accommodation(accommodationId, prices);
        } catch (ClassCastException | NullPointerException e) {
            throw new FileParsingException("Error parsing Accommodation data", e);
        }
    }

    private Advertiser.Price parsePrice(Map<String, Object> priceData) throws FileParsingException {
        try {
            String currency = (String) priceData.get("currency");
            String price = String.valueOf(priceData.get("price"));
            return new Advertiser.Price(currency, price);
        } catch (ClassCastException | NullPointerException e) {
            throw new FileParsingException("Error parsing Advertiser data", e);
        }
    }
}
