package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class DockerContainerStats {

    @JsonProperty("read")
    String datetimeRead

    @JsonProperty("pids_stats")
    Map<String, Long> pidStats

    @JsonProperty("networks")
    Map<String, DockerContainerNetworkStats> networkStats

    @JsonProperty("memory_stats")
    DockerContainerMemoryStats memoryStats

    @JsonProperty("cpu_stats")
    DockerContainerCpuStats cpuStats

    @JsonProperty("precpu_stats")
    DockerContainerCpuStats preCpuStats
}
