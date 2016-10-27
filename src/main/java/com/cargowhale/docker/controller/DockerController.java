package com.cargowhale.docker.controller;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DockerController {

    private final CargoWhaleProperties properties;
    private final RestTemplate restTemplate;

    @Autowired
    public DockerController(final RestTemplate restTemplate, final CargoWhaleProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @RequestMapping(value = "/images",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getImages() {
        String dockerUri = this.properties.getDockerUri();

        return this.restTemplate.getForObject(dockerUri + "/images/json", String.class);
    }

}
