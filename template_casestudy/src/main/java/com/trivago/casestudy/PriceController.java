package com.trivago.casestudy;

import java.util.List;

import com.trivago.casestudy.exceptionHandler.FileParsingException;
import com.trivago.casestudy.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.trivago.casestudy.exceptionHandler.ErrorMessages.*;

@RestController
public class PriceController {

    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

  @GetMapping("/prices/{accommodationId}")
  public ResponseEntity<?> getPrices(@PathVariable("accommodationId") int accommodationId) {
      try {
          List<?> prices = priceService.getPricesByAccommodationId(accommodationId);

          if (prices == null && prices.isEmpty()) {
              return new ResponseEntity<>(ERROR_PRICE_NO_FOUND, HttpStatus.NOT_FOUND);
          } else {
              return new ResponseEntity<>(prices, HttpStatus.OK);
          }
      } catch (FileParsingException e) {
          return new ResponseEntity<>(IO_ERROR_READING_FILE, HttpStatus.INTERNAL_SERVER_ERROR);
      } catch (Exception e) {
          return new ResponseEntity<>(ERROR_UNEXPECTED_ERROR_OCCURRED, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }
}
