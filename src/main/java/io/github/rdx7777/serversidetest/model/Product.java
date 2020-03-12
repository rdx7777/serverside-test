package io.github.rdx7777.serversidetest.model;

import java.math.BigDecimal;

public class Product {

    private String title;
    private Integer kcal_per_100g;
    private BigDecimal unit_price;
    private String description;

    private Product(Builder builder) {
        title = builder.title;
        kcal_per_100g = builder.kcal_per_100g;
        unit_price = builder.unit_price;
        description = builder.description;
    }

    public static Product.Builder builder() {
        return new Product.Builder();
    }

    public static class Builder {

        private String title;
        private Integer kcal_per_100g;
        private BigDecimal unit_price;
        private String description;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withKcal_per_100g(Integer kcal_per_100g) {
            this.kcal_per_100g = kcal_per_100g;
            return this;
        }

        public Builder withUnit_price(BigDecimal unit_price) {
            this.unit_price = unit_price;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
