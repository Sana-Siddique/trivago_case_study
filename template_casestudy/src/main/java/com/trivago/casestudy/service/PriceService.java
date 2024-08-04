package com.trivago.casestudy.service;

import com.trivago.casestudy.component.FileParser;
import com.trivago.casestudy.exceptionHandler.FileParsingException;
import com.trivago.casestudy.model.Advertiser;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceService {

    private final static String JSON_FILE_PATH = "src/main/resources/prices/advertiser_200.json";
    private final static String YAML_FILE_PATH = "src/main/resources/prices/advertiser_100.yaml";


    private final FileParser fileParser;

    public PriceService(FileParser fileParser) {
        this.fileParser = fileParser;
    }

    public List<Advertiser> getAdvertisersFromJson() throws FileParsingException {
        return fileParser.parseJsonFile(JSON_FILE_PATH);
    }

    public List<Advertiser> getAdvertisersFromYaml() throws FileParsingException {
        return fileParser.parseYamlFile(YAML_FILE_PATH);
    }

    public List<Advertiser.Accommodation> getPricesByAccommodationId(int accommodationId) throws FileParsingException {
        List<Advertiser> advertisers = new ArrayList<>();
        advertisers.addAll(getAdvertisersFromJson());
        advertisers.addAll(getAdvertisersFromYaml());

        return advertisers.stream()
            .flatMap(advertiser -> advertiser.getAccommodation().stream())
            .filter(accommodation -> accommodation.getId() == accommodationId)
            .collect(Collectors.toList());
    }

}
