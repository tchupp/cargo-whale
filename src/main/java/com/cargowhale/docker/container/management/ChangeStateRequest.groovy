package com.cargowhale.docker.container.management

import groovy.transform.Canonical

@Canonical
class ChangeStateRequest {

    ContainerChangeState state

}
