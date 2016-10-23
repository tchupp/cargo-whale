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

    @RequestMapping(value = "/containers/{name}/start", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String startContainer(@PathVariable("name") String name){
        this.restTemplate.postForEntity(this.socatUri + "/containers/{name}/start", "",String.class, name);
        return name;
    }

    @RequestMapping(value = "/containers/{name}/stop", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String stopContainer(@PathVariable("name") String name){
        this.restTemplate.postForEntity(this.socatUri + "/containers/{name}/stop", "",String.class, name);
        return name;
    }

    @RequestMapping(value = "/containers/{name}/restart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String restartContainer(@PathVariable("name") String name){
        this.restTemplate.postForEntity(this.socatUri + "/containers/{name}/restart", "",String.class, name);
        return name;
    }

    @RequestMapping(value = "/containers/{name}/kill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String killContainer(@PathVariable("name") String name){
        this.restTemplate.postForEntity(this.socatUri + "/containers/{name}/kill", "",String.class, name);
        return name;
    }

}
