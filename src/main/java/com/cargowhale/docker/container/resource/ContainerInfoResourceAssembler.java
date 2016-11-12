package com.cargowhale.docker.container.resource;

import com.cargowhale.docker.container.ContainerInfoVM;
import com.cargowhale.docker.container.info.ContainerInfoController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ContainerInfoResourceAssembler extends ResourceAssemblerSupport<ContainerInfoVM, ContainerInfoResource> {

    public ContainerInfoResourceAssembler() {
        super(ContainerInfoController.class, ContainerInfoResource.class);
    }

    @Override
    public ContainerInfoResource toResource(final ContainerInfoVM containerInfo) {
        ContainerInfoResource resource = createResourceWithId(containerInfo.getId(), containerInfo);

//        resource.add(linkTo(methodOn(ContainerInfoController.class)));

        return resource;
    }
}
