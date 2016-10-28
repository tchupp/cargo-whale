package com.cargowhale.docker.controller;

import com.cargowhale.docker.container.ContainerInfoCollectionVM;
import com.cargowhale.docker.domain.ChangeStatusRequest;
import com.cargowhale.docker.domain.ChangeStatusResponse;
import com.cargowhale.docker.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContainerController {

    private final ContainerService service;

    @Autowired
    public ContainerController(final ContainerService service) {
        this.service = service;
    }

    @RequestMapping(value = "/containers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerInfoCollectionVM getAllContainers() {
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
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ChangeStatusResponse setContainerStatus(@PathVariable("name") String name, @RequestBody ChangeStatusRequest statusRequest) {
        return this.service.setContainerStatus(name, statusRequest);
    }

}
