package com.cargowhale.docker.controller;

import com.cargowhale.docker.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContainerController {

    private final ContainerService service;

    @Autowired
    public ContainerController(final ContainerService service) {
        this.service = service;
    }

    @RequestMapping(value = "/containers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllContainers() {
        return this.service.getAllContainers();
    }
}
