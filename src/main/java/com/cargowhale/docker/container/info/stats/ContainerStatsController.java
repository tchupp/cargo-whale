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
    private final ContainerStatsProcessor statsProcessor;

    public ContainerStatsController(final ContainerStatsService statsService, final ContainerStatsProcessor statsProcessor) {
        this.statsService = statsService;
        this.statsProcessor = statsProcessor;
    }

    @RequestMapping(value = "/{id}/stats",
        method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerStatsResource getContainerStats(@PathVariable final String id) {
        ContainerStatsResource statsResource = this.statsService.getContainerStats(id);
        return this.statsProcessor.process(statsResource);
    }
}
