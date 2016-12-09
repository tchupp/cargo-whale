package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import com.cargowhale.docker.container.info.ContainerIndexController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContainerListResponseItemResourceAssembler extends ResourceAssemblerSupport<ContainerListItem, ContainerListResponseItemResource> {

    public ContainerListResponseItemResourceAssembler() {
        super(ContainerIndexController.class, ContainerListResponseItemResource.class);
    }

    @Override
    public ContainerListResponseItemResource toResource(final ContainerListItem entity) {
        return createResourceWithId(entity.getId(), entity);
    }

    @Override
    protected ContainerListResponseItemResource instantiateResource(final ContainerListItem entity) {
        return new ContainerListResponseItemResource(entity);
    }
}
