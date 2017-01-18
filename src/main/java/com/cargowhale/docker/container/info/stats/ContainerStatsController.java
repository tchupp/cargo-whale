package com.cargowhale.docker.container.info.stats;

import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/containers")
public class ContainerStatsController {

    private final ContainerStatsService statsService;
    private final ContainerStatsResourceAssembler statsResourceAssembler;

    public ContainerStatsController(final ContainerStatsService statsService, final ContainerStatsResourceAssembler statsResourceAssembler) {
        this.statsService = statsService;
        this.statsResourceAssembler = statsResourceAssembler;
    }

    @RequestMapping(value = "/{id}/stats",
        method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerStatsResource getContainerStats(@PathVariable final String id) {
        ContainerStats containerStats = this.statsService.getContainerStats(id);
        containerStats.setId(id);
        return this.statsResourceAssembler.toResource(containerStats);
    }
}
