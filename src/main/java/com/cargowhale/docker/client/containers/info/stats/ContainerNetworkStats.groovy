package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerNetworkStats {

    Long rxBytes
    Long rxDropped
    Long rxErrors
    Long rxPackets
    Long txBytes
    Long txDropped
    Long txErrors
    Long txPackets

    @JsonProperty("rx_bytes")
    void setRxBytes(Long rxBytes){this.rxBytes = rxBytes}

    @JsonProperty("rxBytes")
    Long getRxBytes(){return this.rxBytes}

    @JsonProperty("rx_dropped")
    void setRxDropped(Long rxDropped){this.rxDropped = rxDropped}

    @JsonProperty("rxDropped")
    Long getRxDropped(){return this.rxDropped}

    @JsonProperty("rx_errors")
    void setRxErrors(Long rxErrors){this.rxErrors = rxErrors}

    @JsonProperty("rxErrors")
    Long getRxErrors(){return this.rxErrors}

    @JsonProperty("rx_packets")
    void setRxPackets(Long rxPackets){this.rxPackets = rxPackets}

    @JsonProperty("rxPackets")
    Long getRxPackets(){return this.rxPackets}

    @JsonProperty("tx_bytes")
    void setTxBytes(Long txBytes){this.txBytes = txBytes}

    @JsonProperty("txBytes")
    Long getTxBytes(){return this.txBytes}

    @JsonProperty("tx_dropped")
    void setTxDropped(Long txDropped){this.txDropped = txDropped}

    @JsonProperty("txDropped")
    Long getTxDropped(){return this.txDropped}

    @JsonProperty("tx_errors")
    void setTxErrors(Long txErrors){this.txErrors = txErrors}

    @JsonProperty("txErrors")
    Long getTxErrors(){return this.txErrors}

    @JsonProperty("tx_packets")
    void setTxPackets(Long rxPackets){this.txPackets = txPackets}

    @JsonProperty("txPackets")
    Long getTxPackets(){return this.txPackets}
}
