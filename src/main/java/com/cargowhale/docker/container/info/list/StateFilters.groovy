package com.cargowhale.docker.container.info.list

import com.cargowhale.docker.client.containers.ContainerState
import groovy.transform.Canonical

@Canonical
class StateFilters {

    Set<ContainerState> state
}
