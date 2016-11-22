package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.ContainerEnumConverter;
import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerSummaryIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContainerInfoController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ContainerState.class, new ContainerEnumConverter());
    }

    private final ContainerInfoService service;

    @Autowired
    public ContainerInfoController(final ContainerInfoService service) {
        this.service = service;
    }

    @RequestMapping(value = "/containers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerSummaryIndex getAllContainers() {
        return this.service.getAllContainers();
    }

    @RequestMapping(value = "/containers",
            params = "state",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerSummaryIndex getContainersFilterByStatus(StateFilters stateFilters) {
        return this.service.getContainersFilterByStatus(stateFilters);
    }

    @RequestMapping(value = "/containers/{containerId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerDetails getContainerById(@PathVariable String containerId) {
        return this.service.getContainerDetailsById(containerId);
    }
}
