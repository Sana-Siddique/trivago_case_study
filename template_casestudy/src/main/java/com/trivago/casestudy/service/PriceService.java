package com.trivago.casestudy.service;

import com.trivago.casestudy.component.FileReaderService;
import com.trivago.casestudy.exceptionHandler.ErrorMessages;
import com.trivago.casestudy.exceptionHandler.FileParsingException;
import com.trivago.casestudy.model.Advertiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceService {

    @Autowired
    FileReaderService fileReaderService;

    /**
     * This method is the main method to get data
     *
     * @param accommodationId refers to the unique accodation ID to fetch the details from files
     * @Approach In this method, first json file will be read and search for the provided accomodation ID
     * if data is found then it will be returned. If data is not found it will then load Yaml file data
     * and search the data in that file
     * @Cacheable once the data is fetched from file it will be stored in redis cache so when it is retrieved again,we will not need to
     * find it in file. This approach is very effective because cache makes retrieval faster and cache can
     * reduced the latency compared to accessing a particular record from file and if frequency
     * of retrieving a particular dataset is higher, then it is more efficient to store it in cache rather than
     * making expensive I/O operation if we have larger datasets.
     */

    @Cacheable(value = "prices", key = "#accommodationId")
    public List<Advertiser.Accommodation> getPricesByAccommodationId(int accommodationId) throws FileParsingException {
        List<Advertiser> advertisers;
        List<Advertiser.Accommodation> accommodations = new ArrayList<>();
        try {
            advertisers = fileReaderService.readAllDataFromJsonFile();
            accommodations = searchInFiles(advertisers, accommodationId);

            if (accommodations.isEmpty()) {
                advertisers = fileReaderService.readAllDataFromYamlFile();
                accommodations = searchInFiles(advertisers, accommodationId);
            }
        } catch (Exception e) {
            throw new FileParsingException(ErrorMessages.ERROR_UNEXPECTED_ERROR_OCCURRED, e);
        }
        return accommodations;
    }


    /**
    * This method is used to search in the given file against the given accommodation ID.
    */
    public List<Advertiser.Accommodation> searchInFiles(List<Advertiser> advertisers,int accommodationId){
        return advertisers.stream()
            .flatMap(advertiser -> advertiser.getAccommodation().stream())
            .filter(accommodation -> accommodation.getId() == accommodationId)
            .collect(Collectors.toList());
    }

}
