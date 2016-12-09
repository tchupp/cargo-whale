package com.cargowhale.docker.index

import com.cargowhale.docker.container.info.ContainerIndexController
import org.springframework.hateoas.ResourceSupport

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

class IndexResource extends ResourceSupport {

    IndexResource() {
        add(linkTo(ContainerIndexController.class).withRel("containers"))
    }
}
