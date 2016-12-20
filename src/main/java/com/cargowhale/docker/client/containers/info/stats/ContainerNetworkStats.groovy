package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerNetworkStats {

    @JsonProperty("rx_bytes")
    Long rxBytes

    @JsonProperty("rx_dropped")
    Long rxDropped

    @JsonProperty("rx_errors")
    Long rxErrors

    @JsonProperty("rx_packets")
    Long rxPackets

    @JsonProperty("tx_bytes")
    Long txBytes

    @JsonProperty("tx_dropped")
    Long txDropped

    @JsonProperty("tx_errors")
    Long txErrors

    @JsonProperty("tx_packets")
    Long txPackets
}
