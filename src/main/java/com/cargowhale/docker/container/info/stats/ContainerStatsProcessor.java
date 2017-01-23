package com.cargowhale.docker.container.info.stats;

import com.cargowhale.docker.container.info.resource.ContainerResourceController;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class ContainerStatsProcessor implements ResourceProcessor<ContainerStatsResource> {

    @Override
    public ContainerStatsResource process(final ContainerStatsResource resource) {

        resource.add(linkTo(methodOn(ContainerResourceController.class).inspectContainer(resource.getContainerId())).withRel("up"));
        resource.add(linkTo(methodOn(ContainerStatsController.class).getContainerStats(resource.getContainerId())).withSelfRel());

        return resource;
    }
}
