package com.cargowhale.docker.controller;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/get/containers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllContainers() {
        String dockerUri = this.properties.getDockerUri();

        return this.restTemplate.getForObject(dockerUri + "/containers/json?all=1", String.class);
    }

    //Filter options are: [created, restarting, running, paused, exited, dead]
    @RequestMapping(value = "/get/containers/{filter}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFilteredContainers(@PathVariable("filter") String filter) {
        String dockerUri = this.properties.getDockerUri();

        String filterString = "{\"status\":[\"" + filter + "\"]}";
        return this.restTemplate.getForObject(dockerUri + "/containers/json?filters={filterString}", String.class, filterString);
    }

    @RequestMapping(value = "/containers/{name}/start",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String startContainer(@PathVariable("name") String name) {
        String dockerUri = this.properties.getDockerUri();

        this.restTemplate.postForObject(dockerUri + "/containers/{name}/start", "", String.class, name);
        return name;
    }

    @RequestMapping(value = "/containers/{name}/stop",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String stopContainer(@PathVariable("name") String name) {
        String dockerUri = this.properties.getDockerUri();

        this.restTemplate.postForObject(dockerUri + "/containers/{name}/stop", "", String.class, name);
        return name;
    }

    @RequestMapping(value = "/containers/{name}/restart",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String restartContainer(@PathVariable("name") String name) {
        String dockerUri = this.properties.getDockerUri();

        this.restTemplate.postForObject(dockerUri + "/containers/{name}/restart", "", String.class, name);
        return name;
    }

    @RequestMapping(value = "/containers/{name}/kill",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String killContainer(@PathVariable("name") String name) {
        String dockerUri = this.properties.getDockerUri();

        this.restTemplate.postForObject(dockerUri + "/containers/{name}/kill", "", String.class, name);
        return name;
    }
}
