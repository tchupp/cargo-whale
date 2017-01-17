package com.cargowhale.docker.container.info.index;

import com.spotify.docker.client.messages.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ContainerMapper {

    /**
     * ATTACHED NETWORK
     */

    @Mappings({
        @Mapping(target = "networkId", expression = "java(network.networkId())"),
        @Mapping(target = "endpointId", expression = "java(network.endpointId())"),
        @Mapping(target = "gateway", expression = "java(network.gateway())"),
        @Mapping(target = "ipAddress", expression = "java(network.ipAddress())"),
        @Mapping(target = "ipPrefixLen", expression = "java(network.ipPrefixLen())"),
        @Mapping(target = "ipv6Gateway", expression = "java(network.ipv6Gateway())"),
        @Mapping(target = "globalIPv6Address", expression = "java(network.globalIPv6Address())"),
        @Mapping(target = "globalIPv6PrefixLen", expression = "java(network.globalIPv6PrefixLen())"),
        @Mapping(target = "macAddress", expression = "java(network.macAddress())")
    })
    ContainerResource.NetworkSettings.AttachedNetwork toAttachedNetwork(final AttachedNetwork network);

    Map<String, ContainerResource.NetworkSettings.AttachedNetwork> toAttachedNetworkMap(Map<String, AttachedNetwork> map);

    /**
     * PORT MAPPING
     */

    ContainerResource.NetworkSettings.PortMapping toPortMapping(final Container.PortMapping portMapping);

    List<ContainerResource.NetworkSettings.PortMapping> toPortMappings(final List<Container.PortMapping> portMapping);

    /**
     * NETWORK SETTINGS
     */

    @Mappings({
        @Mapping(target = "ipAddress", expression = "java(settings.ipAddress())"),
        @Mapping(target = "ipPrefixLen", expression = "java(settings.ipPrefixLen())"),
        @Mapping(target = "gateway", expression = "java(settings.gateway())"),
        @Mapping(target = "bridge", expression = "java(settings.bridge())"),
        @Mapping(target = "ports", expression = "java(toPortMappings(portMappings))"),
        @Mapping(target = "macAddress", expression = "java(settings.macAddress())"),
        @Mapping(target = "networks", expression = "java(toAttachedNetworkMap(settings.networks()))")
    })
    ContainerResource.NetworkSettings toNetworkSettings(final NetworkSettings settings, final List<Container.PortMapping> portMappings);


    /**
     * CONTAINER MOUNT
     */

    @Mappings({
        @Mapping(target = "source", expression = "java(mount.source())"),
        @Mapping(target = "destination", expression = "java(mount.destination())"),
        @Mapping(target = "mode", expression = "java(mount.mode())"),
        @Mapping(target = "rw", expression = "java(mount.rw())")
    })
    ContainerResource.ContainerMount toContainerMount(final ContainerMount mount);

    List<ContainerResource.ContainerMount> toContainerMounts(final List<ContainerMount> mounts);


    /**
     * CONTAINER STATE
     */

    @Mappings({
        @Mapping(target = "state", expression = "java(state.status())"),
        @Mapping(target = "status", expression = "java(status)"),
        @Mapping(target = "running", expression = "java(state.running())"),
        @Mapping(target = "paused", expression = "java(state.paused())"),
        @Mapping(target = "restarting", expression = "java(state.restarting())"),
        @Mapping(target = "pid", expression = "java(state.pid())"),
        @Mapping(target = "exitCode", expression = "java(state.exitCode())"),
        @Mapping(target = "startedAt", expression = "java(state.startedAt())"),
        @Mapping(target = "finishedAt", expression = "java(state.finishedAt())"),
        @Mapping(target = "error", expression = "java(state.error())"),
        @Mapping(target = "oomKilled", expression = "java(state.oomKilled())"),
    })
    ContainerResource.ContainerState toContainerState(final ContainerState state, final String status);


    /**
     * CONTAINER CONFIG
     */

    @Mappings({
        @Mapping(target = "hostname", expression = "java(config.hostname())"),
        @Mapping(target = "attachStdin", expression = "java(config.attachStdin())"),
        @Mapping(target = "attachStdout", expression = "java(config.attachStdout())"),
        @Mapping(target = "attachStderr", expression = "java(config.attachStderr())"),
        @Mapping(target = "portSpecs", expression = "java(config.portSpecs())"),
        @Mapping(target = "tty", expression = "java(config.tty())"),
        @Mapping(target = "openStdin", expression = "java(config.openStdin())"),
        @Mapping(target = "stdinOnce", expression = "java(config.stdinOnce())"),
        @Mapping(target = "env", expression = "java(config.env())"),
        @Mapping(target = "command", expression = "java(command)"),
        @Mapping(target = "workingDir", expression = "java(config.workingDir())"),
        @Mapping(target = "entrypoint", expression = "java(config.entrypoint())"),
        @Mapping(target = "networkDisabled", expression = "java(config.networkDisabled())"),
        @Mapping(target = "onBuild", expression = "java(config.onBuild())")
    })
    ContainerResource.ContainerConfig toContainerConfig(final ContainerConfig config, final String command);


    /**
     * CONTAINER RESOURCE
     */

    @Mappings({
        @Mapping(target = "containerId", expression = "java(container.id())"),
        @Mapping(target = "name", expression = "java(info.name())"),
        @Mapping(target = "image", expression = "java(container.image())"),
        @Mapping(target = "imageId", expression = "java(container.imageId())"),
        @Mapping(target = "config", expression = "java(toContainerConfig(info.config(), container.command()))"),
        @Mapping(target = "created", expression = "java(container.created())"),
        @Mapping(target = "state", expression = "java(toContainerState(info.state(), container.status()))"),
        @Mapping(target = "labels", expression = "java(container.labels())"),
        @Mapping(target = "sizeRw", expression = "java(container.sizeRw())"),
        @Mapping(target = "sizeRootFs", expression = "java(container.sizeRootFs())"),
        @Mapping(target = "networkSettings", expression = "java(toNetworkSettings(info.networkSettings(), container.ports()))"),
        @Mapping(target = "mounts", expression = "java(toContainerMounts(container.mounts()))")
    })
    ContainerResource toResource(final Container container, final ContainerInfo info);
}
