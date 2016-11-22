package com.cargowhale.docker.index

import com.cargowhale.docker.container.info.ContainerInfoController
import org.springframework.hateoas.ResourceSupport

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

public class IndexResource extends ResourceSupport {

    public IndexResource() {
        add(linkTo(ContainerInfoController.class).withRel("containers"))
    }
}
