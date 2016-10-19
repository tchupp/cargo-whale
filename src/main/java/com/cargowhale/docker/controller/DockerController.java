package com.cargowhale.docker.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DockerController {

    @Value("${cargowhale.docker.uri}")
    private String socatUri;

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/images",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getImages() {
        return this.restTemplate.getForEntity(this.socatUri + "/images/json", String.class).getBody();
    }
}
