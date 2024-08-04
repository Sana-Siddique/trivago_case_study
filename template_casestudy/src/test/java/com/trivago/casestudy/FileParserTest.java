package com.trivago.casestudy;

import com.trivago.casestudy.exceptionHandler.FileParsingException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileParserTest {


    /*
     * Test to check if data is fetched successfully from json file by comparing values of few
     * first records and also ensuring size of file
     */
    @Test
    void parseJsonFile() throws IOException, FileParsingException {

        FileParser parser = new FileParser();
        List<Advertiser> advertisers = parser.parseJsonFile("src/main/resources/prices/advertiser_200.json");


        assertNotNull(advertisers);
        assertEquals(1, advertisers.size());

        Advertiser advertiser = advertisers.get(0);
        assertEquals(200, advertiser.getId());
        assertEquals("advertiser", advertiser.getName());
        assertNotNull(advertiser.getAccommodation());
        assertEquals(120, advertiser.getAccommodation().size());

        Advertiser.Accommodation accommodation = advertiser.getAccommodation().get(0);
        assertEquals(71882, accommodation.getId());
        assertNotNull(accommodation.getPrices());
        assertEquals(1, accommodation.getPrices().size());

        Advertiser.Price price = accommodation.getPrices().get(0);
        assertEquals("EUR", price.getCurrency());
        assertEquals("826.74", price.getPrice());
    }

    /*
    * Test to check if data is fetched successfully from yaml file by comparing values of few
    * first records and also ensuring size of file
    */

    @Test
    public void testParseYamlFile() throws FileParsingException {
        FileParser parser = new FileParser();
        List<Advertiser> advertisers = parser.parseYamlFile("src/main/resources/prices/advertiser_100.yaml");

        assertNotNull(advertisers);
        assertEquals(1, advertisers.size());

        Advertiser advertiser = advertisers.get(0);
        assertEquals(100, advertiser.getId());
        assertEquals("advertiser", advertiser.getName());
        assertNotNull(advertiser.getAccommodation());
        assertEquals(1, advertiser.getAccommodation().size());

        Advertiser.Accommodation accommodation = advertiser.getAccommodation().get(0);
        assertEquals(13478, accommodation.getId());
        assertNotNull(accommodation.getPrices());
        assertEquals(1, accommodation.getPrices().size());

        Advertiser.Price price = accommodation.getPrices().get(0);
        assertEquals("EUR", price.getCurrency());
        assertEquals("1351.85", price.getPrice());
    }

    /*
    Test exception handler if we define different file which is not present in the directory
     */

    @Test
    public void testParseInvalidJsonFile() throws FileParsingException {
        FileParser parser = new FileParser();
        assertThrows(FileParsingException.class, () -> {
            parser.parseJsonFile("src/test/resources/prices/file3.json");
        });
    }


}
