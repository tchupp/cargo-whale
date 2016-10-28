package com.cargowhale.docker.controller;

import com.cargowhale.docker.domain.ChangeStatusRequest;
import com.cargowhale.docker.domain.ChangeStatusResponse;
import com.cargowhale.docker.exception.BadFilterException;
import com.cargowhale.docker.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class ContainerController {

    private static final String BAD_FILTER_MESSAGE = "Unrecognised filter value for status: ";

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

    @RequestMapping(value = "/containers/{filter}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFilteredContainers(@PathVariable("filter") String filter) throws BadFilterException {
        String result = this.service.getFilteredContainers(filter);
        return result;
    }

    @RequestMapping(value = "/containers/{name}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ChangeStatusResponse setContainerStatus(@PathVariable("name") String name, @RequestBody ChangeStatusRequest statusRequest) {
        return this.service.setContainerStatus(name, statusRequest);
    }

}
