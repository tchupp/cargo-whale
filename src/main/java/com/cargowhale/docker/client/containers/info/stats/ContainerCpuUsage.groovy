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
    public void setCpuCoreUsage(List<Long> cpuCoreUsage){this.cpuCoreUsage = cpuCoreUsage;}

    @JsonProperty("cpuCoreUsage")
    public List<Long> getCpuCoreUsage(){return this.cpuCoreUsage;}

    @JsonProperty("usage_in_usermode")
    public void setUserModeUsage(Long userModeUsage){this.userModeUsage = userModeUsage;}

    @JsonProperty("userModeUsage")
    public Long getUserModeUsage(){return this.userModeUsage;}

    @JsonProperty("usage_in_kernelmode")
    public void setKernalModeUsage(Long kernalModeUsage){this.kernalModeUsage = kernalModeUsage;}

    @JsonProperty("kernalModeUsage")
    public Long getKernalModeUsage(){return this.kernalModeUsage;}

    @JsonProperty("total_usage")
    public void setTotalUsage(Long totalUsage){this.totalUsage = totalUsage;}

    @JsonProperty("totalUsage")
    public Long getTotalUsage(){return this.totalUsage;}
}
