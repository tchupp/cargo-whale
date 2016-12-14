package com.cargowhale.docker.container.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContainerManagementController {

    private final ContainerManagementService service;

    @Autowired
    public ContainerManagementController(final ContainerManagementService service) {
        this.service = service;
    }

    @RequestMapping(value = "/containers/{name}",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ChangeStateResponse changeContainerState(@PathVariable final String name, @RequestBody final ChangeStateRequest stateRequest) {
        return this.service.changeContainerState(name, stateRequest);
    }
}
