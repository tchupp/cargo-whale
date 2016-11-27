package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.container.info.ContainerDetailsController;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContainerDetailsResourceAssembler extends ResourceAssemblerSupport<ContainerDetails, ContainerDetailsResource> {

    public ContainerDetailsResourceAssembler() {
        super(ContainerDetailsController.class, ContainerDetailsResource.class);
    }

    @Override
    public ContainerDetailsResource toResource(final ContainerDetails entity) {
        return createResourceWithId(entity.getId(), entity);
    }

    @Override
    protected ContainerDetailsResource instantiateResource(final ContainerDetails entity) {
        return new ContainerDetailsResource(entity);
    }
}
