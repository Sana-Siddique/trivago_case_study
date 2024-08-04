package com.trivago.casestudy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileParserTest {

    @Test
    void parseJsonFile() throws IOException {

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
}
