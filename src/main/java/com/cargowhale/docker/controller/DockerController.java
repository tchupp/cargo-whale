package com.cargowhale.docker.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

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

    @RequestMapping(value = "/get/containers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllContainers() {
        return this.restTemplate.getForEntity(this.socatUri + "/containers/json?all=1", String.class).getBody();
    }

    //Filter options are: [created, restarting, running, paused, exited, dead]
    @RequestMapping(value = "/get/containers/{filter}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFilteredContainers(@PathVariable("filter") String filter) {
        String filterString = "{\"status\":[\""+filter+"\"]}";
        return this.restTemplate.getForEntity(this.socatUri + "/containers/json?filters={filterString}", String.class, filterString).getBody();
    }

}
