package io.github.rdx7777.serversidetest.controller;

import io.github.rdx7777.serversidetest.model.WebsiteDataPart;
import io.github.rdx7777.serversidetest.service.ApplicationService;
import io.github.rdx7777.serversidetest.service.ServiceOperationException;

import java.util.Optional;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
public class ApplicationController {

    private final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    private ApplicationService applicationService;
    private UrlValidator validator;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
        this.validator = new UrlValidator();
    }

    @GetMapping(params = "address", produces = "application/json")
    public ResponseEntity<WebsiteDataPart> getData(@RequestParam("address") String address) throws ServiceOperationException {
        if (!validator.isValid(address)) {
            logger.error("Attempt to get website by providing invalid address.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt to get website by providing invalid address.");
        }
        Optional<WebsiteDataPart> dataPart = applicationService.getData(address);
        if (dataPart.isEmpty()) {
            logger.error("Attempt to get website data by providing incorrect address for this application.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt to get website data by providing incorrect address for this application.");
        }
        return ResponseEntity.ok(dataPart.get());
    }
}
