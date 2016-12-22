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
    public void setRxBytes(Long rxBytes){this.rxBytes = rxBytes;}

    @JsonProperty("rxBytes")
    public Long getRxBytes(){return this.rxBytes;}

    @JsonProperty("rx_dropped")
    public void setRxDropped(Long rxDropped){this.rxDropped = rxDropped;}

    @JsonProperty("rxDropped")
    public Long getRxDropped(){return this.rxDropped;}

    @JsonProperty("rx_errors")
    public void setRxErrors(Long rxErrors){this.rxErrors = rxErrors;}

    @JsonProperty("rxErrors")
    public Long getRxErrors(){return this.rxErrors;}

    @JsonProperty("rx_packets")
    public void setRxPackets(Long rxPackets){this.rxPackets = rxPackets;}

    @JsonProperty("rxPackets")
    public Long getRxPackets(){return this.rxPackets;}

    @JsonProperty("tx_bytes")
    public void setTxBytes(Long txBytes){this.txBytes = txBytes;}

    @JsonProperty("txBytes")
    public Long getTxBytes(){return this.txBytes;}

    @JsonProperty("tx_dropped")
    public void setTxDropped(Long txDropped){this.txDropped = txDropped;}

    @JsonProperty("txDropped")
    public Long getTxDropped(){return this.txDropped;}

    @JsonProperty("tx_errors")
    public void setTxErrors(Long txErrors){this.txErrors = txErrors;}

    @JsonProperty("txErrors")
    public Long getTxErrors(){return this.txErrors;}

    @JsonProperty("tx_packets")
    public void setTxPackets(Long rxPackets){this.txPackets = txPackets;}

    @JsonProperty("txPackets")
    public Long getTxPackets(){return this.txPackets;}
}
