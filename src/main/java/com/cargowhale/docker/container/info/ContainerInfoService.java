package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.ContainerInfoClient;
import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.container.ContainerDetails;
import com.cargowhale.docker.container.ContainerInfoCollection;
import com.cargowhale.docker.container.ContainerInfo;
import com.cargowhale.docker.container.StateFilters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContainerInfoService {

    private final ContainerInfoClient client;

    @Autowired
    public ContainerInfoService(final ContainerInfoClient client) {
        this.client = client;
    }

    public ContainerInfoCollection getAllContainers() {
        List<ContainerInfo> allContainers = this.client.getAllContainers();
        return new ContainerInfoCollection(allContainers);
    }

    public ContainerInfoCollection getContainersFilterByStatus(StateFilters stateFilters) {
        DockerContainerFilters dockerContainerFilters = new DockerContainerFilters(stateFilters.getState());

        List<ContainerInfo> filteredContainers = this.client.getFilteredContainers(dockerContainerFilters);
        return new ContainerInfoCollection(filteredContainers);
    }

    public ContainerDetails getContainerDetailsById(final String containerId) {
        return this.client.getContainerDetailsById(containerId);
    }
}
