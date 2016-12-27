package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContainerIndexItemResourceAssembler extends ResourceAssemblerSupport<ContainerListItem, ContainerIndexItemResource> {

    public ContainerIndexItemResourceAssembler() {
        super(ContainerIndexController.class, ContainerIndexItemResource.class);
    }

    @Override
    public ContainerIndexItemResource toResource(final ContainerListItem entity) {
        return createResourceWithId(entity.getId(), entity);
    }

    @Override
    protected ContainerIndexItemResource instantiateResource(final ContainerListItem entity) {
        return new ContainerIndexItemResource(entity);
    }
}
