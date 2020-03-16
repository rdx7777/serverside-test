package io.github.rdx7777.serversidetest.generators;

import io.github.rdx7777.serversidetest.model.Product;
import io.github.rdx7777.serversidetest.model.Total;
import io.github.rdx7777.serversidetest.model.WebsiteDataPart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class WebsiteDataPartGenerator {

    public static WebsiteDataPart getRandomWebsiteDataPart() {
        List<Product> results = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            results.add(ProductGenerator.getRandomProduct());
        }
        BigDecimal gross = BigDecimal.ZERO;
        for (int i = 0; i < 5; i++) {
            gross = gross.add(results.get(i).getUnit_price());
        }
        BigDecimal vat = gross.subtract(gross.divide(BigDecimal.valueOf(1.2), RoundingMode.valueOf(2)));
        return WebsiteDataPart.builder()
            .withResults(results)
            .withTotal(Total.builder()
                .withGross(gross)
                .withVat(vat)
                .build())
            .build();
    }
}
