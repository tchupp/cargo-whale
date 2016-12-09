package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.LogFilters;
import com.cargowhale.docker.container.ContainerEnumConverter;
import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import com.cargowhale.docker.container.info.resource.ContainerDetailsResource;
import com.cargowhale.docker.container.info.resource.ContainerDetailsResourceAssembler;
import com.cargowhale.docker.container.info.resource.ContainerLogsResource;
import com.cargowhale.docker.container.info.resource.ContainerLogsResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/containers")
public class ContainerDetailsController {

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(ContainerState.class, new ContainerEnumConverter());
    }

    private final ContainerInfoService service;
    private final ContainerDetailsResourceAssembler detailsResourceAssembler;
    private final ContainerLogsResourceAssembler logsResourceAssembler;

    @Autowired
    public ContainerDetailsController(final ContainerInfoService service, final ContainerDetailsResourceAssembler detailsResourceAssembler, final ContainerLogsResourceAssembler logsResourceAssembler) {
        this.service = service;
        this.detailsResourceAssembler = detailsResourceAssembler;
        this.logsResourceAssembler = logsResourceAssembler;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerDetailsResource getContainerById(@PathVariable final String id) {
        ContainerDetails containerDetails = this.service.getContainerDetailsById(id);
        return this.detailsResourceAssembler.toResource(containerDetails);
    }

    @RequestMapping(value = "/{id}/logs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerLogsResource getContainerLogsById(@PathVariable final String id, final LogFilters logFilters) {
        ContainerLogs containerLogs = this.service.getContainerLogsById(id, logFilters);
        return this.logsResourceAssembler.toResource(containerLogs);
    }
}
