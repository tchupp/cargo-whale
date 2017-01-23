package com.cargowhale.docker.container.info.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/containers")
public class ContainerLogsController {

    private final ContainerLogsService logsService;
    private final ContainerLogsResourceAssembler logsResourceAssembler;

    @Autowired
    public ContainerLogsController(final ContainerLogsService logsService, final ContainerLogsResourceAssembler logsResourceAssembler) {
        this.logsService = logsService;
        this.logsResourceAssembler = logsResourceAssembler;
    }

    @RequestMapping(value = "/{id}/logs",
        method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerLogsResource getContainerLogsById(@PathVariable final String id, final LogFilters logFilters) {
        ContainerLogs containerLogs = this.logsService.getContainerLogsById(id, logFilters);
        return this.logsResourceAssembler.toResource(containerLogs);
    }
}
