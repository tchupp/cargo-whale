package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerCpuUsage {

    @JsonProperty("percpu_usage")
    List<Long> cpuCoreUsage

    @JsonProperty("usage_in_usermode")
    Long userModeUsage

    @JsonProperty("usage_in_kernelmode")
    Long kernalModeUsage

    @JsonProperty("total_usage")
    Long totalUsage

}
