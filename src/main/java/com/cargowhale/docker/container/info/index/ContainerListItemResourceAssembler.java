package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContainerListItemResourceAssembler extends ResourceAssemblerSupport<ContainerListItem, ContainerListItemResource> {

    public ContainerListItemResourceAssembler() {
        super(ContainerIndexController.class, ContainerListItemResource.class);
    }

    @Override
    public ContainerListItemResource toResource(final ContainerListItem entity) {
        return createResourceWithId(entity.getId(), entity);
    }

    @Override
    protected ContainerListItemResource instantiateResource(final ContainerListItem entity) {
        return new ContainerListItemResource(entity);
    }
}
