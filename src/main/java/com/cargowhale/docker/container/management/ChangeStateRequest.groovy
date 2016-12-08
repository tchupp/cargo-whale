package com.cargowhale.docker.container.management

import com.cargowhale.docker.client.containers.management.state.ContainerChangeState
import groovy.transform.Canonical

@Canonical
class ChangeStateRequest {

    ContainerChangeState state

}
