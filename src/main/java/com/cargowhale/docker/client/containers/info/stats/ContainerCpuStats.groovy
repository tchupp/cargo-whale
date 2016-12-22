package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerCpuStats {

    ContainerCpuUsage cpuUsage
    Long systemCpuUsage
    Map<String, Long> throttlingData

    @JsonProperty("throttlingData")
    public Map<String, Long> getThrottlingData() {
        return this.throttlingData;
    }

    @JsonProperty("throttling_data")
    public void setSystemCpuUsage(Map<String, Long> throttlingData) {
        this.throttlingData = throttlingData;
    }

    @JsonProperty("systemCpuUsage")
    public Long getSystemCpuUsage() {
        return this.systemCpuUsage;
    }

    @JsonProperty("system_cpu_usage")
    public void setSystemCpuUsage(Long systemCpuUsage) {
        this.systemCpuUsage = systemCpuUsage;
    }

    @JsonProperty("cpuUsage")
    public ContainerCpuUsage getCpuUsage() {
        return cpuUsage;
    }

    @JsonProperty("cpu_usage")
    public void setCpuUsage(ContainerCpuUsage cpuUsage) {
        this.cpuUsage = cpuUsage;
    }
}
