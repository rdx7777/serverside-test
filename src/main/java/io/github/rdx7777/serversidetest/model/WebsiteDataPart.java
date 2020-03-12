package io.github.rdx7777.serversidetest.model;

import java.util.ArrayList;
import java.util.List;

public class WebsiteDataPart {

    private List<Product> results;
    private Total total;

    private WebsiteDataPart(Builder builder) {

        results = builder.results;
        total = builder.total;
    }

    public static WebsiteDataPart.Builder builder() {
        return new WebsiteDataPart.Builder();
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
