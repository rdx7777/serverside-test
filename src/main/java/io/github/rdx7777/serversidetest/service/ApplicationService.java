package io.github.rdx7777.serversidetest.service;

import io.github.rdx7777.serversidetest.model.Product;
import io.github.rdx7777.serversidetest.model.Total;
import io.github.rdx7777.serversidetest.model.WebsiteDataPart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApplicationService {

    private final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    public Optional<WebsiteDataPart> getData(String address) throws ServiceOperationException {
        String body = getWebsiteBody(address);
        if (body == null || body.isEmpty()) {
            logger.error("Website body is empty.");
            throw new IllegalArgumentException("Website body cannot be empty.");
        }
        try {
            if (body.contains("productNameAndPromotions")) {
                return getResult(address, body);
            }
        } catch (NoSuchElementException e) {
            String message = "An error occurred during getting website body.";
            logger.error(message, e);
            throw new ServiceOperationException(message, e);
        }
        return Optional.empty();
    }

    private String getWebsiteBody(String address) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(URI.create(address),
            HttpMethod.GET,
            HttpEntity.EMPTY,
            String.class);
        return exchange.getBody();
    }

    private Optional<WebsiteDataPart> getResult(String address, String body) {
        List<String> addressList = getProductAddressList(address, body);
        List<Product> productList = getProductList(addressList);
        BigDecimal gross = BigDecimal.ZERO;
        for (Product product : productList) {
            gross = gross.add(product.getUnit_price());
        }
        BigDecimal vat = gross.subtract(gross.divide(BigDecimal.valueOf(1.2), RoundingMode.valueOf(2)));
        Total total = Total.builder()
            .gross(gross)
            .vat(vat)
            .build();
        return Optional.of(WebsiteDataPart.builder()
            .results(productList)
            .total(total)
            .build());
    }

    private List<Product> getProductList(List<String> addressList) {
        String productBody;
        Elements elements;
        List<Product> productList = new ArrayList<>();
        String title;
        int kcal_per_100g;
        BigDecimal unit_price;
        String description;
        for (String url : addressList) {
            productBody = getWebsiteBody(url);
            elements = Jsoup.parse(productBody).getAllElements();
            title = getProductTitle(elements);
            kcal_per_100g = getProductEnergyInfo(elements);
            unit_price = getProductUnitPrice(elements);
            description = getProductDescription(elements);
            if (kcal_per_100g != 0) {
                productList.add(getProductWithEnergyInfo(title, kcal_per_100g, unit_price, description));
            } else {
                productList.add(getProductWithoutEnergyInfo(title, unit_price, description));
            }
        }
        return productList;
    }

    private Product getProductWithEnergyInfo(String title, Integer kcal_per_100g, BigDecimal unit_price, String description) {
        return Product.builder()
            .title(title)
            .kcal_per_100g(kcal_per_100g)
            .unit_price(unit_price)
            .description(description)
            .build();
    }

    private Product getProductWithoutEnergyInfo(String title, BigDecimal unit_price, String description) {
        return Product.builder()
            .title(title)
            .unit_price(unit_price)
            .description(description)
            .build();
    }

    private String getProductTitle(Elements elements) {
        return elements
            .stream()
            .map(e -> e.getElementsByClass("productTitleDescriptionContainer"))
            .map(String::valueOf)
            .map(e -> StringUtils.substringBetween(e, "<h1>", "</h1>"))
            .findFirst()
            .orElse("");
    }

    private Integer getProductEnergyInfo(Elements elements) {
        return Integer.parseInt(elements
            .stream()
            .map(e -> e.getElementsByClass("tableRow0"))
            .map(String::valueOf)
            .map(e -> StringUtils.substringBetween(e, "<td class=\"tableRow0\">", "kcal</td>"))
            .filter(Objects::nonNull)
            .findAny()
            .orElse("0"));
    }

    private BigDecimal getProductUnitPrice(Elements elements) {
        return BigDecimal.valueOf(Double.parseDouble(elements
            .stream()
            .map(e -> e.getElementsByClass("pricePerUnit"))
            .map(String::valueOf)
            .map(e -> StringUtils.substringBetween(e, "£", "<abbr title=\"per\">"))
            .findFirst()
            .orElse("0")));
    }

    private String getProductDescription(Elements elements) {
        return elements
            .stream()
            .map(e -> e.getElementsByClass("productText"))
            .map(String::valueOf)
            .map(e -> StringUtils.substringBetween(e, "<p>", "</p>"))
            .findFirst()
            .orElse("");
    }

    private List<String> getProductAddressList(String address, String body) {
        List<String> originalAddressList = Jsoup.parse(body)
            .getAllElements()
            .stream()
            .map(e -> e.getElementsByClass("productNameAndPromotions"))
            .map(String::valueOf)
            .map(e -> StringUtils.substringBetween(e, "href=\"", "\">"))
            .filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<String> addressList = new ArrayList<>();
        String nextUrlPart;
        String newUrl;
        for (String url : originalAddressList) {
            nextUrlPart = StringUtils.substringAfterLast(url, "../");
            newUrl = StringUtils.substringBefore(address, "webapp").concat(nextUrlPart);
            addressList.add(newUrl);
        }
        return addressList;
    }
}
