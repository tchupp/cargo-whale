package com.cargowhale.docker.client;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContainerClient {

    private final CargoWhaleProperties properties;
    private final RestTemplate restTemplate;

    @Autowired
    public ContainerClient(final RestTemplate restTemplate, final CargoWhaleProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public String getAllContainers() {
        String dockerUri = this.properties.getDockerUri();

        return this.restTemplate.getForObject(dockerUri + "/containers/json?all=1", String.class);
    }

    //Filter options are: [created, restarting, running, paused, exited, dead]
    public String getFilteredContainers(String filter){
        String dockerUri = this.properties.getDockerUri();

        String filterString = "{\"status\":[\""+filter+"\"]}";
        return this.restTemplate.getForObject(dockerUri + "/containers/json?filters={filterString}", String.class, filterString);
    }

    //Status options are: [start, stop, restart, kill]
    public String setContainerStatus(String name, String status) {
        String dockerUri = this.properties.getDockerUri();
        this.restTemplate.postForObject(dockerUri + "/containers/{name}/{status}", null, String.class, name, status);
        return name;
    }
}
