package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerStats {

    @JsonProperty("read")
    String datetimeRead

    @JsonProperty("pids_stats")
    Map<String, Long> pidStats

    @JsonProperty("networks")
    Map<String, ContainerNetworkStats> networkStats

    @JsonProperty("memory_stats")
    ContainerMemoryStats memoryStats

    @JsonProperty("cpu_stats")
    ContainerCpuStats cpuStats

    @JsonProperty("precpu_stats")
    ContainerCpuStats preCpuStats
}
