package com.trivago.casestudy.component;

import com.trivago.casestudy.configuration.FileProperties;
import com.trivago.casestudy.exceptionHandler.FileParsingException;
import com.trivago.casestudy.model.Advertiser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileReaderService {

    private final FileProperties fileProperties;

    private final FileParser fileParser;

    public FileReaderService(FileProperties fileProperties, FileParser fileParser) {
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

}
