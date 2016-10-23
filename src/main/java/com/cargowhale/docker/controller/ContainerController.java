package com.cargowhale.docker.controller;

import com.cargowhale.docker.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nick on 21/10/16.
 */
@RestController
public class ContainerController {

    @Autowired
    private ContainerService service;

    @RequestMapping(value = "/containers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllContainers() {
        return this.service.getAllContainers();
    }

    @RequestMapping(value = "/containers/{filter}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getFilteredContainers(@PathVariable("filter") String filter) {
        return this.service.getFilteredContainers(filter);
    }

}
