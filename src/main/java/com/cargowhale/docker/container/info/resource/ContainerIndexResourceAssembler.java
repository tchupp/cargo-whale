package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.container.info.ContainerIndexController;
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

    private final ContainerListResponseItemResourceAssembler resourceAssembler;

    @Autowired
    public ContainerIndexResourceAssembler(final ContainerListResponseItemResourceAssembler resourceAssembler) {
        super(ContainerIndexController.class, ContainerIndexResource.class);
        this.resourceAssembler = resourceAssembler;
    }

    @Override
    public ContainerIndexResource toResource(final ContainerIndex index) {
        ContainerIndexResource resource = createResourceWithId("", index);

        resource.add(linkTo(methodOn(IndexController.class).index()).withRel("up"));

        return resource;
    }

    @Override
    protected ContainerIndexResource instantiateResource(final ContainerIndex entity) {
        List<ContainerListResponseItemResource> summaryResourceList = this.resourceAssembler.toResources(entity.getContainers());
        return new ContainerIndexResource(summaryResourceList, entity.getStateMetadata());
    }
}
