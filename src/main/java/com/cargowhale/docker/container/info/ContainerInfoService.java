package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.container.ContainerInfoCollectionVM;
import com.cargowhale.docker.container.ContainerInfoVM;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.domain.ChangeStatusRequest;
import com.cargowhale.docker.domain.ChangeStatusResponse;
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

    public ChangeStatusResponse setContainerStatus(String name, ChangeStatusRequest statusRequest) {
        String newStatus = statusRequest.getStatus();
        String containerName = this.client.setContainerStatus(name, newStatus);

        return new ChangeStatusResponse(containerName);
    }
}
