package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerCpuStats {

    ContainerCpuUsage cpuUsage
    Long systemCpuUsage
    Map<String, Long> throttlingData

    @JsonProperty("throttlingData")
    Map<String, Long> getThrottlingData() { return this.throttlingData }

    @JsonProperty("throttling_data")
    void setSystemCpuUsage(Map<String, Long> throttlingData) { this.throttlingData = throttlingData }

    @JsonProperty("systemCpuUsage")
    Long getSystemCpuUsage() { return this.systemCpuUsage }

    @JsonProperty("system_cpu_usage")
    void setSystemCpuUsage(Long systemCpuUsage) { this.systemCpuUsage = systemCpuUsage }

    @JsonProperty("cpuUsage")
    ContainerCpuUsage getCpuUsage() { return cpuUsage }

    @JsonProperty("cpu_usage")
    void setCpuUsage(ContainerCpuUsage cpuUsage) { this.cpuUsage = cpuUsage }
}
