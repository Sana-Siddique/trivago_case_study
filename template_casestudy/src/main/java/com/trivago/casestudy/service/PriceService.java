package com.trivago.casestudy.service;

import com.trivago.casestudy.component.FileParser;
import com.trivago.casestudy.configuration.FileProperties;
import com.trivago.casestudy.exceptionHandler.FileParsingException;
import com.trivago.casestudy.model.Advertiser;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceService {

    private final FileProperties fileProperties;

    private final FileParser fileParser;

    public PriceService(FileProperties fileProperties, FileParser fileParser) {
        this.fileProperties = fileProperties;
        this.fileParser = fileParser;
    }



    public List<Advertiser> getAdvertisersFromJson() throws FileParsingException {
        return fileParser.parseJsonFile(fileProperties.getJsonFilePath());
    }

    public List<Advertiser> getAdvertisersFromYaml() throws FileParsingException {
        return fileParser.parseYamlFile(fileProperties.getYamlFilePath());
    }

    public List<Advertiser.Accommodation> getPricesByAccommodationId(int accommodationId) throws FileParsingException {
        List<Advertiser> advertisers = new ArrayList<>();
       try {
           advertisers.addAll(getAdvertisersFromJson());
           advertisers.addAll(getAdvertisersFromYaml());

           return advertisers.stream()
               .flatMap(advertiser -> advertiser.getAccommodation().stream())
               .filter(accommodation -> accommodation.getId() == accommodationId)
               .collect(Collectors.toList());
       }catch (Exception e){
           e.getMessage();
           return null;
       }
    }

}
