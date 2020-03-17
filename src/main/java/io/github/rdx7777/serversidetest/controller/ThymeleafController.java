package io.github.rdx7777.serversidetest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.github.rdx7777.serversidetest.model.WebsiteDataPart;
import io.github.rdx7777.serversidetest.service.ApplicationService;
import io.github.rdx7777.serversidetest.service.ServiceOperationException;

import java.util.Optional;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/console")
public class ThymeleafController {

    private final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);

    private final ApplicationService applicationService;

    public ThymeleafController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public String showInputAddressForm() {
        return "input";
    }

    @SuppressWarnings("DuplicatedCode")
    @PostMapping(path = "/json", produces = "application/json")
    public @ResponseBody String showResult(@RequestParam(value = "address", required = false) String address) throws ServiceOperationException, JsonProcessingException {
        UrlValidator validator = new UrlValidator();
        if (!validator.isValid(address)) {
            logger.error("Attempt to get website by providing invalid address.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt to get website by providing invalid address.");
        }
        Optional<WebsiteDataPart> dataPart = applicationService.getData(address);
        if (dataPart.isEmpty()) {
            logger.error("Attempt to get website data by providing incorrect address for this application.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt to get website data by providing incorrect address for this application.");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(dataPart.get());
    }
}
