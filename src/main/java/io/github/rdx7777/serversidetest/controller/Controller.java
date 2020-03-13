package io.github.rdx7777.serversidetest.controller;

import io.github.rdx7777.serversidetest.model.WebsiteDataPart;
import io.github.rdx7777.serversidetest.service.Service;
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
public class Controller {

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping(params = "address", produces = "application/json")
    public ResponseEntity<WebsiteDataPart> getData(@RequestParam("address") String address) throws ServiceOperationException {
        Optional<WebsiteDataPart> dataPart = service.getData(address);
        if (dataPart.isEmpty()) {
            logger.error("Attempt to get website data by incorrect or non existing address.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt to get website data by incorrect or non existing address.");
        }
        return ResponseEntity.ok(dataPart.get());
    }
}
