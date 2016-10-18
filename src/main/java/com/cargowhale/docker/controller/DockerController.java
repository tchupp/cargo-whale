package com.cargowhale.docker.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DockerController {

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/images",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getImages() {
        return this.restTemplate.getForEntity("http://127.0.0.1:2375/images/json", String.class).getBody();
    }
}
