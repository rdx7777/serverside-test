package io.github.rdx7777.serversidetest.model;

import java.math.BigDecimal;

public class Total {

    private BigDecimal gross;
    private BigDecimal vat;

    private Total(Builder builder) {
        gross = builder.gross;
        vat = builder.vat;
    }

    public static Total.Builder builder() {
        return new Total.Builder();
    }

    public static class Builder {

        private BigDecimal gross;
        private BigDecimal vat;

        public Builder withGross(BigDecimal gross) {
            this.gross = gross;
            return this;
        }

        public Builder withVat(BigDecimal vat) {
            this.vat = vat;
            return this;
        }

        public Total build() {
            return new Total(this);
        }
    }
}
