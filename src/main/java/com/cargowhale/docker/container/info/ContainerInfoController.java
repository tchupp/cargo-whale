package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.ContainerEnumConverter;
import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerSummaryIndex;
import com.cargowhale.docker.container.info.resource.ContainerSummaryIndexResource;
import com.cargowhale.docker.container.info.resource.ContainerSummaryIndexResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/containers")
public class ContainerInfoController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ContainerState.class, new ContainerEnumConverter());
    }

    private final ContainerSummaryIndexResourceAssembler indexResourceAssembler;
    private final ContainerInfoService service;

    @Autowired
    public ContainerInfoController(final ContainerInfoService service, final ContainerSummaryIndexResourceAssembler indexResourceAssembler) {
        this.service = service;
        this.indexResourceAssembler = indexResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerSummaryIndexResource getAllContainers() {
        ContainerSummaryIndex summaryIndex = this.service.getAllContainers();
        return this.indexResourceAssembler.toResource(summaryIndex);
    }

    @RequestMapping(method = RequestMethod.GET,
            params = "state",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerSummaryIndexResource getContainersFilterByStatus(StateFilters stateFilters) {
        ContainerSummaryIndex summaryIndex = this.service.getContainersFilterByStatus(stateFilters);
        return this.indexResourceAssembler.toResource(summaryIndex);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerDetails getContainerById(@PathVariable String id) {
        return this.service.getContainerDetailsById(id);
    }
}
