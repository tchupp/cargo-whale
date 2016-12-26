package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerCpuUsage {

    List<Long> cpuCoreUsage
    Long userModeUsage
    Long kernalModeUsage
    Long totalUsage

    @JsonProperty("percpu_usage")
    void setCpuCoreUsage(List<Long> cpuCoreUsage){this.cpuCoreUsage = cpuCoreUsage}

    @JsonProperty("cpuCoreUsage")
    List<Long> getCpuCoreUsage(){return this.cpuCoreUsage}

    @JsonProperty("usage_in_usermode")
    void setUserModeUsage(Long userModeUsage){this.userModeUsage = userModeUsage}

    @JsonProperty("userModeUsage")
    Long getUserModeUsage(){return this.userModeUsage}

    @JsonProperty("usage_in_kernelmode")
    void setKernalModeUsage(Long kernalModeUsage){this.kernalModeUsage = kernalModeUsage}

    @JsonProperty("kernalModeUsage")
    Long getKernalModeUsage(){return this.kernalModeUsage}

    @JsonProperty("total_usage")
    void setTotalUsage(Long totalUsage){this.totalUsage = totalUsage}

    @JsonProperty("totalUsage")
    Long getTotalUsage(){return this.totalUsage}
}
