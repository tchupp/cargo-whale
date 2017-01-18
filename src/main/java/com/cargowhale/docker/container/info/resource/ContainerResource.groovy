package com.cargowhale.docker.container.info.resource

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.core.Relation

@Canonical
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(value = "container", collectionRelation = "containers")
class ContainerResource extends ResourceSupport {

    @JsonProperty("id")
    String containerId
    String name
    String image
    String imageId
    ContainerState state
    ContainerConfig config
    Long created
    Map<String, String> labels
    Long sizeRw
    Long sizeRootFs
    NetworkSettings networkSettings
    List<ContainerMount> mounts

    @Canonical
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class ContainerConfig {

        String hostname
        Boolean attachStdin
        Boolean attachStdout
        Boolean attachStderr
        List<String> portSpecs
        Boolean tty
        Boolean openStdin
        Boolean stdinOnce
        List<String> env
        String command
        String workingDir
        List<String> entrypoint
        Boolean networkDisabled
        List<String> onBuild
    }

    @Canonical
    static class ContainerState {

        String state
        String status
        Boolean running
        Boolean paused
        Boolean restarting
        Integer pid
        Integer exitCode
        Date startedAt
        Date finishedAt
        String error
        Boolean oomKilled
    }

    @Canonical
    static class ContainerMount {

        String source
        String destination
        String mode
        Boolean rw
    }

    @Canonical
    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class NetworkSettings {

        String ipAddress
        Integer ipPrefixLen
        String gateway
        String bridge
        List<PortMapping> ports
        String macAddress
        Map<String, AttachedNetwork> networks

        @Canonical
        static class PortMapping {

            int privatePort
            int publicPort
            String type
            String ip
        }

        @Canonical
        static class AttachedNetwork {

            String networkId
            String endpointId
            String gateway
            String ipAddress
            Integer ipPrefixLen
            String ipv6Gateway
            String globalIPv6Address
            Integer globalIPv6PrefixLen
            String macAddress
        }
    }
}
