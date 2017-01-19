package com.cargowhale.docker.container.info.stats;

import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/containers")
public class ContainerStatsController {

    private final ContainerStatsService statsService;

    public ContainerStatsController(final ContainerStatsService statsService) {
        this.statsService = statsService;
    }

    @RequestMapping(value = "/{id}/stats",
        method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerStatsResource getContainerStats(@PathVariable final String id) {
        return this.statsService.getContainerStats(id);
    }
}
