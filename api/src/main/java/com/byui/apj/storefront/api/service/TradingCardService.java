package com.byui.apj.storefront.api.service;

import com.byui.apj.storefront.api.model.TradingCard;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TradingCardService {
    private final List<TradingCard> cards;

    public TradingCardService() {
        this.cards = new ArrayList<>();
        try {
            ClassPathResource resource = new ClassPathResource("pioneers.csv");
            Reader reader = new InputStreamReader(resource.getInputStream());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            for (CSVRecord record : records) {
                Long id = Long.parseLong(record.get("ID"));
                String name = record.get("Name");
                String specialty = record.get("Specialty");
                String contribution = record.get("Contribution");
                BigDecimal price = new BigDecimal(record.get("Price"));
                String imageUrl = record.get("ImageUrl");
                cards.add(new TradingCard(id, name, specialty, contribution, price, imageUrl));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<TradingCard> filterCards(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort) {
        Stream<TradingCard> cards = this.cards.stream();
        if (sort.equals("name")) {
            cards = cards.sorted(Comparator.comparing(TradingCard::getName));
        }
        if (sort.equals("price")) {
            cards = cards.sorted(Comparator.comparing(TradingCard::getPrice));
        }
        return cards.filter((card) -> card.getPrice().compareTo(minPrice) >= 0  && card.getPrice().compareTo(maxPrice) >= 0)
                .filter((card) -> card.getSpecialty().equalsIgnoreCase(specialty))
                .toList();
    }

    public List<TradingCard> getAllCards(int page, int size) {
        int start = Math.min(page * size, this.cards.size());
        int end = Math.min(start + size, this.cards.size());
        return cards.subList(start, end);
    }

    public List<TradingCard> searchCards(String query) {
        return this.cards.stream()
                .filter(card -> card.getName().toLowerCase().contains(query.toLowerCase()) || card.getSpecialty().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
