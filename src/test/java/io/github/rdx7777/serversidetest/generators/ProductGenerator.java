package io.github.rdx7777.serversidetest.generators;

import io.github.rdx7777.serversidetest.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class ProductGenerator {

    public static Product getRandomProduct() {
        String title = WordGenerator.getRandomWord();
        Integer kcal_per_100g = ThreadLocalRandom.current().nextInt(1, 100);
        BigDecimal unit_price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 10)).setScale(2, RoundingMode.HALF_EVEN);
        String description = WordGenerator.getRandomWord();

        return Product.builder()
            .title(title)
            .kcal_per_100g(kcal_per_100g)
            .unit_price(unit_price)
            .description(description)
            .build();
    }
}
