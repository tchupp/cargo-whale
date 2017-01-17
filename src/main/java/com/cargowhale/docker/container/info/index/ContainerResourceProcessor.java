package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.container.info.ContainerDetailsController;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class ContainerResourceProcessor implements ResourceProcessor<ContainerResource> {

    @Override
    public ContainerResource process(final ContainerResource resource) {

        resource.add(linkTo(methodOn(ContainerIndexController.class).listContainers()).withRel("up"));
        resource.add(linkTo(methodOn(ContainerDetailsController.class).getContainerById(resource.getContainerId())).withSelfRel());
        resource.add(linkTo(methodOn(ContainerDetailsController.class).getContainerLogsById(resource.getContainerId(), new LogFilters())).withRel("logs"));
        resource.add(linkTo(methodOn(ContainerDetailsController.class).getContainerStatsById(resource.getContainerId())).withRel("stats"));
        resource.add(linkTo(methodOn(ContainerDetailsController.class).getContainerProcessesById(resource.getContainerId())).withRel("top"));

        return resource;
    }
}
