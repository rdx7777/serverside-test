package io.github.rdx7777.serversidetest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Product {

    private final String title;
    private final Integer kcal_per_100g;
    private final BigDecimal unit_price;
    private final String description;
}
