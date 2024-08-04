package com.trivago.casestudy.service;

import com.trivago.casestudy.component.FileParser;
import com.trivago.casestudy.exceptionHandler.FileParsingException;
import com.trivago.casestudy.model.Advertiser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PriceServiceTest {


    @Mock
    private FileParser fileParser;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    Test case to check fetch price by accomodation ID which is present in any of json and yaml file
     */

    @Test
    void testGetPricesByAccommodationId() throws FileParsingException {
        Advertiser.Accommodation accommodation1 = new Advertiser.Accommodation(1, Arrays.asList(new Advertiser.Price("EUR", "100.00")));
        Advertiser.Accommodation accommodation2 = new Advertiser.Accommodation(2, Arrays.asList(new Advertiser.Price("USD", "200.00")));
        Advertiser advertiser = new Advertiser("Advertiser1", 100, Arrays.asList(accommodation1, accommodation2));

        when(fileParser.parseJsonFile("src/main/resources/prices/advertiser_200.json")).thenReturn(Arrays.asList(advertiser));
        when(fileParser.parseYamlFile("src/main/resources/prices/advertiser_100.yaml")).thenReturn(Arrays.asList());
        List<Advertiser.Accommodation> result = priceService.getPricesByAccommodationId(1);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("EUR", result.get(0).getPrices().get(0).getCurrency());
        assertEquals("100.00", result.get(0).getPrices().get(0).getPrice());
    }

    /*
    Test case to check fetch price by accomodation ID which is not present in any of json and yaml file
     */
    @Test
    void testGetPricesByAccommodationId_NotFound() throws FileParsingException {
        Advertiser.Accommodation accommodation1 = new Advertiser.Accommodation(1, Arrays.asList(new Advertiser.Price("EUR", "100.00")));
        Advertiser advertiser = new Advertiser("Advertiser1", 100, Arrays.asList(accommodation1));

        when(fileParser.parseJsonFile("src/main/resources/prices/advertiser_200.json")).thenReturn(Arrays.asList(advertiser));
        when(fileParser.parseYamlFile("src/main/resources/prices/advertiser_100.yaml")).thenReturn(Arrays.asList());
        List<Advertiser.Accommodation> result = priceService.getPricesByAccommodationId(99);
        assertEquals(0, result.size());
    }
}
