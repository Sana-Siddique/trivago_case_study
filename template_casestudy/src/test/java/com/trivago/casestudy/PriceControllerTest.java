package com.trivago.casestudy;

import com.trivago.casestudy.exceptionHandler.FileParsingException;
import com.trivago.casestudy.model.Advertiser;
import com.trivago.casestudy.service.PriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;


@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @Test
    void testGetPrices_Success() throws Exception {
        List<Advertiser.Accommodation> mockAccommodations = List.of(
            new Advertiser.Accommodation(1, List.of(new Advertiser.Price("EUR", "826.74")))
        );

        when(priceService.getPricesByAccommodationId(71882)).thenReturn(mockAccommodations);

        mockMvc.perform(MockMvcRequestBuilders.get("/prices/71882")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

    @Test
    void testGetPrices_NoData() throws Exception {
        when(priceService.getPricesByAccommodationId(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/prices/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("No prices found for the given accommodation ID"));
    }

    @Test
    void testGetPrices_FileParsingException() throws Exception {
        when(priceService.getPricesByAccommodationId(1)).thenThrow(new FileParsingException("File parsing error"));

        mockMvc.perform(MockMvcRequestBuilders.get("/prices/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.content().string("Error parsing files"));
    }
}
