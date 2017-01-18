package com.cargowhale.docker.container.info.stats;

import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import com.cargowhale.docker.container.info.logs.ContainerLogsController;
import com.cargowhale.docker.container.info.resource.ContainerResourceController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ContainerStatsResourceAssembler extends ResourceAssemblerSupport<ContainerStats, ContainerStatsResource> {

    public ContainerStatsResourceAssembler() {
        super(ContainerLogsController.class, ContainerStatsResource.class);
    }

    @Override
    public ContainerStatsResource toResource(final ContainerStats entity) {
        ContainerStatsResource resource = createResourceWithId(entity.getId(), entity);

        resource.add(linkTo(methodOn(ContainerResourceController.class).inspectContainer(entity.getId())).withRel("up"));

        return resource;
    }

    @Override
    protected ContainerStatsResource createResourceWithId(final Object id, final ContainerStats entity) {
        ContainerStatsResource resource = new ContainerStatsResource(entity);

        resource.add(linkTo(methodOn(ContainerStatsController.class).getContainerStats(entity.getId())).withSelfRel());

        return resource;
    }
}
