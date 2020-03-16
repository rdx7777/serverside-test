package io.github.rdx7777.serversidetest.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Total {

    private final BigDecimal gross;
    private final BigDecimal vat;

    private Total(Builder builder) {
        gross = builder.gross;
        vat = builder.vat;
    }

    public static Total.Builder builder() {
        return new Total.Builder();
    }

    public BigDecimal getGross() {
        return gross;
    }

    public BigDecimal getVat() {
        return vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Total)) return false;
        Total total = (Total) o;
        return Objects.equals(gross, total.gross) &&
            Objects.equals(vat, total.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gross, vat);
    }

    @Override
    public String toString() {
        return "Total{" +
            "gross=" + gross +
            ", vat=" + vat +
            '}';
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
