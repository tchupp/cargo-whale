package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ListContainersParam;
import com.cargowhale.docker.container.info.details.InspectContainerClient;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.cargowhale.docker.client.containers.ListContainersParam.allContainers;

@Service
public class ContainerIndexService {

    private final ListContainersClient listContainersClient;
    private final InspectContainerClient inspectContainerClient;
    private final ContainerMapper mapper;
    private final ContainerResourceProcessor resourceProcessor;

    public ContainerIndexService(final ListContainersClient listContainersClient, final InspectContainerClient inspectContainerClient, final ContainerMapper mapper, final ContainerResourceProcessor resourceProcessor) {
        this.listContainersClient = listContainersClient;
        this.inspectContainerClient = inspectContainerClient;
        this.mapper = mapper;
        this.resourceProcessor = resourceProcessor;
    }

    List<ContainerResource> getContainers(final ListContainersParam... filters) {
        List<Container> containers = this.listContainersClient.listContainers(filters);
        Function<Container, ContainerResource> containerResourceMappingFunction = container -> {
            ContainerInfo info = this.inspectContainerClient.inspectContainer(container.id());
            return this.mapper.toResource(container, info);
        };
        List<ContainerResource> containerResources = containers.parallelStream().map(containerResourceMappingFunction).collect(Collectors.toList());
        return containerResources.stream().map(this.resourceProcessor::process).collect(Collectors.toList());
    }

    ContainerResource getContainer(final String containerId) {
        ContainerInfo info = this.inspectContainerClient.inspectContainer(containerId);
        List<Container> containers = this.listContainersClient.listContainers(allContainers());

        for (final Container container : containers) {
            if (container.id().equals(containerId)) {
                return this.resourceProcessor.process(this.mapper.toResource(container, info));
            }
        }

        return null;
    }
}
