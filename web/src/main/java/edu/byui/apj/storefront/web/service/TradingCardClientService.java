package edu.byui.apj.storefront.web.service;

import edu.byui.apj.storefront.web.model.TradingCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TradingCardClientService {
    private final String tradingCardServiceUrl;
    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public TradingCardClientService(@Value("${trading-card.service.url}") String tradingCardServiceUrl) {
        this.tradingCardServiceUrl = tradingCardServiceUrl;
        this.restTemplate = new RestTemplate();
        this.webClient = WebClient.builder().baseUrl(tradingCardServiceUrl).build();
    }

    // Returns the list of cards starting at position page * size and returning size elements.
    public List<TradingCard> getAllCardsPaginated(int page, int size) {
        ResponseEntity<List<TradingCard>> response = restTemplate.exchange(tradingCardServiceUrl + "/api/cards?page=" + page + "&size=" + size, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    // Returns the list resulting in filtering by minPrice, maxPrice or specialty, then sorting by sort
    // Sort can be "name" or "price"
    public List<TradingCard> filterAndSort(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort) {
        ResponseEntity<List<TradingCard>> response = restTemplate.exchange(tradingCardServiceUrl + "/api/cards/filter?minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&specialty=" + specialty + "&sort=" + sort, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    // Returns the list of cards resulting in the query string (case insensitive) found in the name or contribution.
    public List<TradingCard> searchByNameOrContribution(String query) {
        ResponseEntity<List<TradingCard>> response = restTemplate.exchange(tradingCardServiceUrl + "/api/cards/search?query=" + query, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return response.getBody();
    }


}
