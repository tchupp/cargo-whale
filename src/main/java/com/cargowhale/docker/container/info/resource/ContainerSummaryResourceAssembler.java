package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.container.info.ContainerSummaryController;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContainerSummaryResourceAssembler extends ResourceAssemblerSupport<ContainerSummary, ContainerSummaryResource> {

    public ContainerSummaryResourceAssembler() {
        super(ContainerSummaryController.class, ContainerSummaryResource.class);
    }

    @Override
    public ContainerSummaryResource toResource(final ContainerSummary entity) {
        return createResourceWithId(entity.getId(), entity);
    }

    @Override
    protected ContainerSummaryResource instantiateResource(final ContainerSummary entity) {
        return new ContainerSummaryResource(entity);
    }
}
