package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.container.info.ContainerInfoController;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContainerSummaryResourceAssembler extends ResourceAssemblerSupport<ContainerSummary, ContainerSummaryResource> {

    public ContainerSummaryResourceAssembler() {
        super(ContainerInfoController.class, ContainerSummaryResource.class);
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
