package com.cargowhale.docker.index

import com.cargowhale.docker.container.info.ContainerSummaryController
import org.springframework.hateoas.ResourceSupport

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

public class IndexResource extends ResourceSupport {

    public IndexResource() {
        add(linkTo(ContainerSummaryController.class).withRel("containers"))
    }
}
