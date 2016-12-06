package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.client.LogFilters;
import com.cargowhale.docker.container.info.ContainerDetailsController;
import com.cargowhale.docker.container.info.ContainerSummaryController;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ContainerDetailsResourceAssembler extends ResourceAssemblerSupport<ContainerDetails, ContainerDetailsResource> {

    public ContainerDetailsResourceAssembler() {
        super(ContainerDetailsController.class, ContainerDetailsResource.class);
    }

    @Override
    public ContainerDetailsResource toResource(final ContainerDetails entity) {
        ContainerDetailsResource resource = createResourceWithId(entity.getId(), entity);

        resource.add(linkTo(methodOn(ContainerSummaryController.class).getAllContainers()).withRel("up"));
        resource.add(linkTo(methodOn(ContainerDetailsController.class).getContainerLogsById(entity.getId(), new LogFilters())).withRel("logs"));
        resource.add(linkTo(methodOn(ContainerDetailsController.class).getContainerProcessesById(entity.getId())).withRel("processes"));

        return resource;
    }

    @Override
    protected ContainerDetailsResource instantiateResource(final ContainerDetails entity) {
        return new ContainerDetailsResource(entity);
    }
}
