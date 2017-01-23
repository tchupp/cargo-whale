package com.cargowhale.docker.container.info.resource;

import com.cargowhale.docker.index.IndexController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class ContainerIndexResourceAssembler extends ResourceAssemblerSupport<List<ContainerResource>, ContainerIndexResource> {

    private final ContainerIndexResourceMetadataService service;

    public ContainerIndexResourceAssembler(final ContainerIndexResourceMetadataService service) {
        super(ContainerResourceController.class, ContainerIndexResource.class);
        this.service = service;
    }

    @Override
    public ContainerIndexResource toResource(final List<ContainerResource> containers) {
        Map<ContainerState, Integer> stateMetadata = this.service.getStateMetadata();
        ContainerIndexResource resource = new ContainerIndexResource(containers, stateMetadata);

        ContainerResourceController containerResourceController = methodOn(ContainerResourceController.class);
        resource.add(linkTo(containerResourceController.listContainers()).withRel(ContainerState.ALL.getState()));
        resource.add(linkTo(containerResourceController.listContainers(ContainerState.CREATED)).withRel(ContainerState.CREATED.getState()));
        resource.add(linkTo(containerResourceController.listContainers(ContainerState.RESTARTING)).withRel(ContainerState.RESTARTING.getState()));
        resource.add(linkTo(containerResourceController.listContainers(ContainerState.RUNNING)).withRel(ContainerState.RUNNING.getState()));
        resource.add(linkTo(containerResourceController.listContainers(ContainerState.PAUSED)).withRel(ContainerState.PAUSED.getState()));
        resource.add(linkTo(containerResourceController.listContainers(ContainerState.EXITED)).withRel(ContainerState.EXITED.getState()));
        resource.add(linkTo(containerResourceController.listContainers(ContainerState.DEAD)).withRel(ContainerState.DEAD.getState()));
        resource.add(linkTo(methodOn(IndexController.class).index()).withRel("up"));

        return resource;
    }
}
