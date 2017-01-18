package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.container.info.logs.ContainerLogsController;
import com.cargowhale.docker.container.info.logs.LogFilters;
import com.cargowhale.docker.container.info.stats.ContainerStatsController;
import com.cargowhale.docker.container.info.top.ContainerProcessController;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class ContainerResourceProcessor implements ResourceProcessor<ContainerResource> {

    @Override
    public ContainerResource process(final ContainerResource resource) {

        resource.add(linkTo(methodOn(ContainerResourceController.class).listContainers()).withRel("up"));
        resource.add(linkTo(methodOn(ContainerResourceController.class).inspectContainer(resource.getContainerId())).withSelfRel());
        resource.add(linkTo(methodOn(ContainerLogsController.class).getContainerLogsById(resource.getContainerId(), new LogFilters())).withRel("logs"));
        resource.add(linkTo(methodOn(ContainerStatsController.class).getContainerStats(resource.getContainerId())).withRel("stats"));

        if (resource.getState().getRunning()) {
            resource.add(linkTo(methodOn(ContainerProcessController.class).getContainerProcesses(resource.getContainerId())).withRel("top"));
        }

        return resource;
    }
}
