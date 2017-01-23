package com.cargowhale.docker.container.info.top;

import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/containers")
public class ContainerProcessController {

    private final ContainerProcessService processService;
    private final ContainerProcessesResourceAssembler processesResourceAssembler;

    public ContainerProcessController(final ContainerProcessService processService, final ContainerProcessesResourceAssembler processesResourceAssembler) {
        this.processService = processService;
        this.processesResourceAssembler = processesResourceAssembler;
    }

    @RequestMapping(value = "/{id}/top",
        method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerProcessesResource getContainerProcesses(@PathVariable final String id) {
        ContainerProcessIndex containerProcessIndex = this.processService.getContainerProcesses(id);
        return this.processesResourceAssembler.toResource(containerProcessIndex);
    }
}
