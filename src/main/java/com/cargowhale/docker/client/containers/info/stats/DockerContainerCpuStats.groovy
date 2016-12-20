package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class DockerContainerCpuStats {

    @JsonProperty("cpu_usage")
    DockerContainerCpuUsage cpuUsage

    @JsonProperty("system_cpu_usage")
    Long systemCpuUsage

    @JsonProperty("throttling_data")
    Map<String, Long> throttlingData
}
