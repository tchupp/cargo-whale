package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.container.info.ContainerSummaryController;
import com.cargowhale.docker.container.info.model.ContainerSummaryIndex;
import com.cargowhale.docker.index.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ContainerSummaryIndexResourceAssembler extends ResourceAssemblerSupport<ContainerSummaryIndex, ContainerSummaryIndexResource> {

    private final ContainerSummaryResourceAssembler summaryResourceAssembler;

    @Autowired
    public ContainerSummaryIndexResourceAssembler(final ContainerSummaryResourceAssembler summaryResourceAssembler) {
        super(ContainerSummaryController.class, ContainerSummaryIndexResource.class);
        this.summaryResourceAssembler = summaryResourceAssembler;
    }

    @Override
    public ContainerSummaryIndexResource toResource(final ContainerSummaryIndex index) {
        ContainerSummaryIndexResource resource = createResourceWithId("", index);

        resource.add(linkTo(methodOn(IndexController.class).index()).withRel("up"));

        return resource;
    }

    @Override
    protected ContainerSummaryIndexResource instantiateResource(final ContainerSummaryIndex entity) {
        List<ContainerSummaryResource> summaryResourceList = this.summaryResourceAssembler.toResources(entity.getContainers());
        return new ContainerSummaryIndexResource(summaryResourceList);
    }
}
