package io.github.rdx7777.serversidetest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class WebsiteDataPart {

    private final List<Product> results;
    private final Total total;
}
