package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerMemoryStats {

    @JsonProperty("max_usage")
    Long maxUsage

    @JsonProperty("usage")
    Long usage

    @JsonProperty("failcnt")
    Long failCount

    @JsonProperty("limit")
    Long limit

}
