package com.cargowhale.docker.container.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContainerManagementController {

    @Autowired
    private ContainerManagementService service;

    @RequestMapping(value = "/containers/{name}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ChangeStateResponse changeContainerState(@PathVariable("name") String name, @RequestBody ChangeStateRequest stateRequest) {
        return this.service.changeContainerState(name, stateRequest);
    }
}
