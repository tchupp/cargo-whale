package com.cargowhale.docker.container.info.details

import com.cargowhale.docker.client.containers.ContainerState
import com.cargowhale.docker.client.containers.info.inspect.ContainerDetailsState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.TupleConstructor
import org.springframework.hateoas.ResourceSupport

@TupleConstructor(force = true)
@Canonical
class ContainerDetailsStateResource extends ResourceSupport {

    @JsonProperty("status")
    ContainerState state
    Integer pid
    String error
    Integer exitCode
    String startedTime
    String finishedTime

    ContainerDetailsStateResource(ContainerDetailsState entity) {
        this.state = entity.state
        this.pid = entity.pid
        this.error = entity.error
        this.exitCode = entity.exitCode
        this.startedTime = entity.startedTime
        this.finishedTime = entity.finishedTime
    }
}
