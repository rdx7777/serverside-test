package io.github.rdx7777.serversidetest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Total {

    private final BigDecimal gross;
    private final BigDecimal vat;
}
