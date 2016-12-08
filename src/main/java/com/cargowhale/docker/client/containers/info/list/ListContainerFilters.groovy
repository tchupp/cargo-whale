package com.cargowhale.docker.client.containers.info.list

import com.cargowhale.docker.client.containers.ContainerState
import groovy.json.JsonOutput
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ListContainerFilters {

    final Set<ContainerState> status

    ListContainerFilters(final ContainerState[] status) {
        this.status = new LinkedHashSet<>(Arrays.asList(status))
    }

    String toJson() {
        return JsonOutput.toJson(this).toLowerCase()
    }
}
