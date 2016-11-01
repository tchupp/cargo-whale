package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.ContainerInfoCollectionVM;
import com.cargowhale.docker.container.StateFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ContainerInfoController {

    private final ContainerInfoService service;

    @Autowired
    public ContainerInfoController(final ContainerInfoService service) {
        this.service = service;
    }

    @RequestMapping(value = "/containers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerInfoCollectionVM getAllContainers() {
        return this.service.getAllContainers();
    }

    @RequestMapping(value = "/containers",
            params = "state",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerInfoCollectionVM getContainersFilterByStatus(StateFilters stateFilters) {
        return this.service.getContainersFilterByStatus(stateFilters);
    }
}
