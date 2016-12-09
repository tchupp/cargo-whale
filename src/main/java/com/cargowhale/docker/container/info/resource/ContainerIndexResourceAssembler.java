package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.container.info.ContainerSummaryController;
import com.cargowhale.docker.container.info.model.ContainerIndex;
import com.cargowhale.docker.index.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ContainerIndexResourceAssembler extends ResourceAssemblerSupport<ContainerIndex, ContainerIndexResource> {

    private final ContainerSummaryResourceAssembler summaryResourceAssembler;

    @Autowired
    public ContainerIndexResourceAssembler(final ContainerSummaryResourceAssembler summaryResourceAssembler) {
        super(ContainerSummaryController.class, ContainerIndexResource.class);
        this.summaryResourceAssembler = summaryResourceAssembler;
    }

    @Override
    public ContainerIndexResource toResource(final ContainerIndex index) {
        ContainerIndexResource resource = createResourceWithId("", index);

        resource.add(linkTo(methodOn(IndexController.class).index()).withRel("up"));

        return resource;
    }

    @Override
    protected ContainerIndexResource instantiateResource(final ContainerIndex entity) {
        List<ContainerSummaryResource> summaryResourceList = this.summaryResourceAssembler.toResources(entity.getContainers());
        return new ContainerIndexResource(summaryResourceList, entity.getStateMetadata());
    }
}
