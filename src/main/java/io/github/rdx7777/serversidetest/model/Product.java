package io.github.rdx7777.serversidetest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    private final String title;
    private final Integer kcal_per_100g;
    private final BigDecimal unit_price;
    private final String description;

    private Product(Builder builder) {
        title = builder.title;
        kcal_per_100g = builder.kcal_per_100g;
        unit_price = builder.unit_price;
        description = builder.description;
    }

    public static Product.Builder builder() {
        return new Product.Builder();
    }

    public String getTitle() {
        return title;
    }

    public Integer getKcal_per_100g() {
        return kcal_per_100g;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(title, product.title) &&
            Objects.equals(kcal_per_100g, product.kcal_per_100g) &&
            Objects.equals(unit_price, product.unit_price) &&
            Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, kcal_per_100g, unit_price, description);
    }

    @Override
    public String toString() {
        return "Product{" +
            "title='" + title + '\'' +
            ", kcal_per_100g=" + kcal_per_100g +
            ", unit_price=" + unit_price +
            ", description='" + description + '\'' +
            '}';
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
