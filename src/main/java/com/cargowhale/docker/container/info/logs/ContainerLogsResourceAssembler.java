package com.cargowhale.docker.container.info.logs;

import com.cargowhale.docker.container.info.index.ContainerIndexController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ContainerLogsResourceAssembler extends ResourceAssemblerSupport<ContainerLogs, ContainerLogsResource> {

    public ContainerLogsResourceAssembler() {
        super(ContainerLogsController.class, ContainerLogsResource.class);
    }

    @Override
    public ContainerLogsResource toResource(final ContainerLogs entity) {
        ContainerLogsResource resource = new ContainerLogsResource(entity);

        resource.add(linkTo(methodOn(ContainerLogsController.class).getContainerLogsById(entity.getId(), new LogFilters())).withSelfRel());
        resource.add(linkTo(methodOn(ContainerIndexController.class).inspectContainer(entity.getId())).withRel("up"));

        return resource;
    }

}
