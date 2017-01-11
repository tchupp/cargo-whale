package com.cargowhale.docker.container.info.index

import com.cargowhale.docker.client.containers.ContainerState
import groovy.transform.Canonical
import org.springframework.hateoas.ResourceSupport

@Canonical
class ContainerIndexResource extends ResourceSupport {

    List<ContainerResource> containers
    Map<ContainerState, Integer> stateMetadata
}
