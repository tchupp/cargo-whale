package com.cargowhale.docker.container

import com.cargowhale.docker.client.containers.ContainerState
import groovy.transform.Canonical

@Canonical
class StateFilters {

    ContainerState[] state
}
