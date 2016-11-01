package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.ContainerInfoCollectionVM;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.domain.ChangeStatusRequest;
import com.cargowhale.docker.domain.ChangeStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/containers/{name}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ChangeStatusResponse setContainerStatus(@PathVariable("name") String name, @RequestBody ChangeStatusRequest statusRequest) {
        return this.service.setContainerStatus(name, statusRequest);
    }

}
