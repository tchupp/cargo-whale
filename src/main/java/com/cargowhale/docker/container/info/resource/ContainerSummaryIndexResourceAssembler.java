package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.container.info.ContainerInfoController;
import com.cargowhale.docker.container.info.model.ContainerSummaryIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContainerSummaryIndexResourceAssembler extends ResourceAssemblerSupport<ContainerSummaryIndex, ContainerSummaryIndexResource> {

    private final ContainerSummaryResourceAssembler summaryResourceAssembler;

    @Autowired
    public ContainerSummaryIndexResourceAssembler(final ContainerSummaryResourceAssembler summaryResourceAssembler) {
        super(ContainerInfoController.class, ContainerSummaryIndexResource.class);
        this.summaryResourceAssembler = summaryResourceAssembler;
    }

    @Override
    public ContainerSummaryIndexResource toResource(final ContainerSummaryIndex index) {
        return createResourceWithId("", index);
    }

    @Override
    protected ContainerSummaryIndexResource instantiateResource(final ContainerSummaryIndex entity) {
        List<ContainerSummaryResource> summaryResourceList = this.summaryResourceAssembler.toResources(entity.getContainers());
        return new ContainerSummaryIndexResource(summaryResourceList);
    }
}
