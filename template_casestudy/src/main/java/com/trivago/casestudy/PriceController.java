package com.trivago.casestudy;

import java.util.List;

import com.trivago.casestudy.exceptionHandler.FileParsingException;
import com.trivago.casestudy.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

  @GetMapping("/prices/{accommodationId}")
  public List<?> getPrices(@PathVariable("accommodationId") int accommodationId) {
      try{
          return priceService.getPricesByAccommodationId(accommodationId);
      } catch (FileParsingException e) {
        throw new RuntimeException("System Failure", e);
    }
  }
}
