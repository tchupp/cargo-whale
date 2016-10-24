package com.cargowhale.docker.controller;

import com.cargowhale.docker.client.ContainerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ContainerController {

    private final ContainerClient service;

    @Autowired
    public ContainerController(final ContainerClient service) {
        this.service = service;
    }

    @RequestMapping(value = "/containers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllContainers() {
        return this.service.getAllContainers();
    }

    @RequestMapping(value = "/containers/{filter}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFilteredContainers(@PathVariable("filter") String filter) {
        return this.service.getFilteredContainers(filter);
    }

    @RequestMapping(value = "/containers/{name}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String setContainerStatus(@PathVariable("name") String name, @RequestBody Map<String, String> statusMap){
        return this.service.setContainerStatus(name, statusMap.get("status"));
    }

}
