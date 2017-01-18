package com.cargowhale.docker.index

import com.cargowhale.docker.container.info.resource.ContainerResourceController
import org.springframework.hateoas.ResourceSupport

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

class IndexResource extends ResourceSupport {

    IndexResource() {
        add(linkTo(ContainerResourceController.class).withRel("containers"))
    }
}
