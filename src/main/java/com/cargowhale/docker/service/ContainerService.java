package com.cargowhale.docker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nick on 22/10/16.
 */
@Component
public class ContainerService {

    @Value("${cargowhale.docker.uri}")
    protected String socatUri;

    private RestTemplate restTemplate = new RestTemplate();

    public String getAllContainers() {
        return this.restTemplate.getForEntity(this.socatUri + "/containers/json?all=1", String.class).getBody();
    }

    //Filter options are: [created, restarting, running, paused, exited, dead]
    public String getFilteredContainers(String filter){
        String filterString = "{\"status\":[\""+filter+"\"]}";
        return this.restTemplate.getForEntity(this.socatUri + "/containers/json?filters={filterString}", String.class, filterString).getBody();
    }
}
