package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.container.info.ContainerDetailsController;
import com.cargowhale.docker.container.info.index.ContainerIndexController;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ContainerLogsResourceAssembler extends ResourceAssemblerSupport<ContainerLogs, ContainerLogsResource> {

    public ContainerLogsResourceAssembler() {
        super(ContainerDetailsController.class, ContainerLogsResource.class);
    }

    @Override
    public ContainerLogsResource toResource(final ContainerLogs entity) {
        ContainerLogsResource resource = createResourceWithId(entity.getId(), entity);

        resource.add(linkTo(methodOn(ContainerIndexController.class).inspectContainer(entity.getId())).withRel("up"));

        return resource;
    }

    @Override
    protected ContainerLogsResource createResourceWithId(final Object id, final ContainerLogs entity) {
        ContainerLogsResource resource = new ContainerLogsResource(entity);

        resource.add(linkTo(methodOn(ContainerDetailsController.class).getContainerLogsById(entity.getId(), new LogFilters())).withSelfRel());

        return resource;
    }
}
