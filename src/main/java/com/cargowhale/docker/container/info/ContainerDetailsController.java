package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.ContainerEnumConverter;
import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.info.model.ContainerDetails;
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

    @Autowired
    public ContainerDetailsController(final ContainerInfoService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ContainerDetails getContainerById(@PathVariable String id) {
        return this.service.getContainerDetailsById(id);
    }

    @RequestMapping(value = "/{id}/logs",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String getContainerLogsById(@PathVariable String id,
                                       @RequestParam(value = "follow", defaultValue = "0") String follow,
                                       @RequestParam(value = "stdout", defaultValue = "1") String stdOut,
                                       @RequestParam(value = "stderr", defaultValue = "1") String stdErr,
                                       @RequestParam(value = "since", defaultValue = "0") String since,
                                       @RequestParam(value = "timestamps", defaultValue = "1") String timestamps,
                                       @RequestParam(value = "tail", defaultValue = "100") String tail) {
        return this.service.getContainerLogsById(id, follow, stdOut, stdErr, since, timestamps, tail);
    }
}
