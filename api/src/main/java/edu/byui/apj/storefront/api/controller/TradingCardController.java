package edu.byui.apj.storefront.api.controller;

import edu.byui.apj.storefront.api.model.TradingCard;
import edu.byui.apj.storefront.api.service.TradingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TradingCardController {
    private final TradingCardService tradingCardService;

    @Autowired
    public TradingCardController(TradingCardService tradingCardService) {
        this.tradingCardService = tradingCardService;
    }

    @GetMapping("/cards")
    public List<TradingCard> getAllCards(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return tradingCardService.getAllCards(page, size);
    }

    @GetMapping("/cards/filter")
    public List<TradingCard> filterCards(@RequestParam(required = false) BigDecimal minPrice,
                                         @RequestParam(required = false) BigDecimal maxPrice,
                                         @RequestParam(required = false) String specialty,
                                         @RequestParam(required = false) String sort) {
        return tradingCardService.filterCards(minPrice, maxPrice, specialty, sort);
    }

    @GetMapping("/cards/search")
    public List<TradingCard> searchCards(@RequestParam String query) {
        return tradingCardService.searchCards(query);
    }


}
