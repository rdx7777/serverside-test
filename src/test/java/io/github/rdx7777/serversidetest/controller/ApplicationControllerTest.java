package io.github.rdx7777.serversidetest.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.rdx7777.serversidetest.generators.WebsiteDataPartGenerator;
import io.github.rdx7777.serversidetest.generators.WordGenerator;
import io.github.rdx7777.serversidetest.model.WebsiteDataPart;
import io.github.rdx7777.serversidetest.service.ApplicationService;
import io.github.rdx7777.serversidetest.service.ServiceOperationException;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    @MockBean
    private ApplicationService applicationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnData() throws Exception {
        WebsiteDataPart dataPart = WebsiteDataPartGenerator.getRandomWebsiteDataPart();
        String address = WordGenerator.getRandomWord();
        when(applicationService.getData(address)).thenReturn(Optional.of(dataPart));

        String url = String.format("/?address=%s", address);

        mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(mapper.writeValueAsString(dataPart)));

        verify(applicationService).getData(address);
    }

    @Test
    void shouldReturnNotFoundStatusDuringGettingDataWhenProvidedAddressIsIncorrect() throws Exception {
        String address = WordGenerator.getRandomWord();

        String url = String.format("/?address=%s", address);

        mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(applicationService).getData(address);
    }

    @Test
    void shouldReturnNotAcceptableStatusDuringGettingDataWithNotSupportedMediaType() throws Exception {
        String address = WordGenerator.getRandomWord();

        String url = String.format("/?address=%s", address);

        mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_XML))
            .andExpect(status().isNotAcceptable());

        verify(applicationService, never()).getData(address);
    }

    @Test
    void shouldReturnInternalServerErrorDuringGettingDataWhenSomethingWentWrongOnServer() throws Exception {
        String address = WordGenerator.getRandomWord();
        when(applicationService.getData(address)).thenThrow(new ServiceOperationException());

        String url = String.format("/?address=%s", address);

        mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());

        verify(applicationService).getData(address);
    }
}
