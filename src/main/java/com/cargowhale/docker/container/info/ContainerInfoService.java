package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.ContainerInfoClient;
import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.container.ContainerInfoCollectionVM;
import com.cargowhale.docker.container.ContainerInfoVM;
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

    public ContainerInfoCollectionVM getAllContainers() {
        List<ContainerInfoVM> allContainers = this.client.getAllContainers();
        return new ContainerInfoCollectionVM(allContainers);
    }

    public ContainerInfoCollectionVM getContainersFilterByStatus(StateFilters stateFilters) {
        DockerContainerFilters dockerContainerFilters = new DockerContainerFilters(stateFilters.getState());

        List<ContainerInfoVM> filteredContainers = this.client.getFilteredContainers(dockerContainerFilters);
        return new ContainerInfoCollectionVM(filteredContainers);
    }
}
