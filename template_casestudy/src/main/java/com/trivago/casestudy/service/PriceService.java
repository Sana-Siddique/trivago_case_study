package com.trivago.casestudy.service;

import com.trivago.casestudy.component.FileParser;
import com.trivago.casestudy.configuration.FileProperties;
import com.trivago.casestudy.exceptionHandler.ErrorMessages;
import com.trivago.casestudy.exceptionHandler.FileParsingException;
import com.trivago.casestudy.model.Advertiser;
import org.springframework.cache.annotation.Cacheable;
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

    public List<Advertiser> readAllDataFromJsonFile(){
        List<Advertiser> advertisers = new ArrayList<>();
        try{
            advertisers.addAll(getAdvertisersFromJson());
        }catch (Exception exception){
            exception.getMessage();
        }
        return advertisers;
    }

    public List<Advertiser> readAllDataFromYamlFile(){
        List<Advertiser> advertisers = new ArrayList<>();
        try{
            advertisers.addAll(getAdvertisersFromYaml());
        }catch (Exception exception){
            exception.getMessage();
        }
        return advertisers;
    }

    @Cacheable(value = "prices", key = "#accommodationId")
    public List<Advertiser.Accommodation> getPricesByAccommodationId(int accommodationId) throws FileParsingException {
        List<Advertiser> advertisers;
        List<Advertiser.Accommodation> accommodations = new ArrayList<>();
        try {
            advertisers = readAllDataFromJsonFile();
            accommodations = advertisers.stream()
                .flatMap(advertiser -> advertiser.getAccommodation().stream())
                .filter(accommodation -> accommodation.getId() == accommodationId)
                .collect(Collectors.toList());

            if (!accommodations.isEmpty()) {
                return accommodations;
            }

            advertisers = readAllDataFromYamlFile();
            accommodations = advertisers.stream()
                .flatMap(advertiser -> advertiser.getAccommodation().stream())
                .filter(accommodation -> accommodation.getId() == accommodationId)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new FileParsingException(ErrorMessages.ERROR_UNEXPECTED_ERROR_OCCURRED, e);
        }
        return accommodations;
    }

}
