package io.github.rdx7777.serversidetest.controller;

import io.github.rdx7777.serversidetest.model.WebsiteDataPart;
import io.github.rdx7777.serversidetest.service.ApplicationService;
import io.github.rdx7777.serversidetest.service.ServiceOperationException;

import java.util.Optional;

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

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping(params = "address", produces = "application/json")
    public ResponseEntity<WebsiteDataPart> getData(@RequestParam("address") String address) throws ServiceOperationException {
        Optional<WebsiteDataPart> dataPart = applicationService.getData(address);
        if (dataPart.isEmpty()) {
            logger.error("Attempt to get website data by incorrect or non existing address.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt to get website data by incorrect or non existing address.");
        }
        return ResponseEntity.ok(dataPart.get());
    }
}
