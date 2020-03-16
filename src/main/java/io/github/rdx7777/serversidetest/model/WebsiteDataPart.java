package io.github.rdx7777.serversidetest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebsiteDataPart {

    private final List<Product> results;
    private final Total total;

    private WebsiteDataPart(Builder builder) {
        results = builder.results;
        total = builder.total;
    }

    public static WebsiteDataPart.Builder builder() {
        return new WebsiteDataPart.Builder();
    }

    public List<Product> getResults() {
        return results;
    }

    public Total getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebsiteDataPart)) return false;
        WebsiteDataPart that = (WebsiteDataPart) o;
        return Objects.equals(results, that.results) &&
            Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results, total);
    }

    @Override
    public String toString() {
        return "WebsiteDataPart{" +
            "results=" + results +
            ", total=" + total +
            '}';
    }

    public static class Builder {

        private List<Product> results;
        private Total total;

        public Builder withResults(List<Product> results) {
            this.results = results != null ? new ArrayList(results) : new ArrayList();
            return this;
        }

        public Builder withTotal(Total total) {
            this.total = total;
            return this;
        }

        public WebsiteDataPart build() {
            return new WebsiteDataPart(this);
        }
    }
}
