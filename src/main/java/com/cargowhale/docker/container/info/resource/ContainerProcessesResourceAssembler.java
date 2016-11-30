package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.container.info.ContainerDetailsController;
import com.cargowhale.docker.container.info.model.ContainerProcesses;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ContainerProcessesResourceAssembler extends ResourceAssemblerSupport<ContainerProcesses, ContainerProcessesResource> {

    public ContainerProcessesResourceAssembler() { super(ContainerDetailsController.class, ContainerProcessesResource.class); }

    @Override
    public ContainerProcessesResource toResource(final ContainerProcesses entity) {
        ContainerProcessesResource resource = createResourceWithId(entity.getId(), entity);

        resource.add(linkTo(methodOn(ContainerDetailsController.class).getContainerById(entity.getId())).withRel("up"));

        return resource;
    }

    @Override
    protected ContainerProcessesResource createResourceWithId(final Object id, final ContainerProcesses entity) {
        ContainerProcessesResource resource = new ContainerProcessesResource(entity);

        resource.add(linkTo(methodOn(ContainerDetailsController.class).getContainerProcessesById(entity.getId())).withSelfRel());

        return resource;
    }

}
