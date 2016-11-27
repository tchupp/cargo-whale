package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.ContainerEnumConverter;
import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.LogFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.resource.ContainerDetailsResource;
import com.cargowhale.docker.container.info.resource.ContainerDetailsResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/containers")
public class ContainerDetailsController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ContainerState.class, new ContainerEnumConverter());
    }

    private final ContainerInfoService service;
    private final ContainerDetailsResourceAssembler resourceAssembler;

    @Autowired
    public ContainerDetailsController(final ContainerInfoService service, final ContainerDetailsResourceAssembler resourceAssembler) {
        this.service = service;
        this.resourceAssembler = resourceAssembler;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerDetailsResource getContainerById(@PathVariable String id) {
        ContainerDetails containerDetails = this.service.getContainerDetailsById(id);
        return this.resourceAssembler.toResource(containerDetails);
    }

    @RequestMapping(value = "/{id}/logs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getContainerLogsById(@PathVariable String id, LogFilters logFilters) {
        return this.service.getContainerLogsById(id, logFilters);
    }
}
