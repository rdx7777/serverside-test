package io.github.rdx7777.serversidetest.service;

import io.github.rdx7777.serversidetest.model.WebsiteDataPart;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@org.springframework.stereotype.Service
public class Service {

    private final Logger logger = LoggerFactory.getLogger(Service.class);

    public Optional<WebsiteDataPart> getData(String url) throws ServiceOperationException {
        return Optional.empty();
    }
}
