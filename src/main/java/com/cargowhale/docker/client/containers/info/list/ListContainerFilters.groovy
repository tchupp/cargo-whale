package com.cargowhale.docker.client.containers.info.list

import com.cargowhale.docker.client.containers.ContainerState
import com.cargowhale.docker.client.core.QueryParameters
import groovy.json.JsonOutput
import groovy.transform.Canonical
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Canonical
class ListContainerFilters implements QueryParameters {

    final Set<ContainerState> status

    ListContainerFilters(final Set<ContainerState> status) {
        this.status = status
    }

    @Override
    MultiValueMap<String, String> asQueryParameters() {
        def queryParameters = new LinkedMultiValueMap<String, String>()
        queryParameters.add("filters", JsonOutput.toJson(this).toLowerCase())

        return queryParameters
    }
}
