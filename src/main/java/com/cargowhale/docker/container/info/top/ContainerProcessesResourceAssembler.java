package com.cargowhale.docker.container.info.top;

import com.cargowhale.docker.container.info.logs.ContainerLogsController;
import com.cargowhale.docker.container.info.resource.ContainerResourceController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ContainerProcessesResourceAssembler extends ResourceAssemblerSupport<ContainerProcessIndex, ContainerProcessesResource> {

    public ContainerProcessesResourceAssembler() {
        super(ContainerLogsController.class, ContainerProcessesResource.class);
    }

    @Override
    public ContainerProcessesResource toResource(final ContainerProcessIndex entity) {
        ContainerProcessesResource resource = new ContainerProcessesResource(entity);

        resource.add(linkTo(methodOn(ContainerProcessController.class).getContainerProcesses(entity.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ContainerResourceController.class).inspectContainer(entity.getId())).withRel("up"));

        return resource;
    }

}
