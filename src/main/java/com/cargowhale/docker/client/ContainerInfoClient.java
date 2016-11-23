package com.cargowhale.docker.client;

import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ContainerInfoClient {

    private final RestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;
    private final JsonConverter converter;

    @Autowired
    public ContainerInfoClient(final RestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder, final JsonConverter converter) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
        this.converter = converter;
    }

    public List<ContainerSummary> getAllContainers() {
        String containersEndpoint = this.endpointBuilder.getContainersInfoEndpoint();

        ContainerSummary[] containerSummaryArray = this.restTemplate.getForObject(containersEndpoint + "?all=1", ContainerSummary[].class);
        return Arrays.asList(containerSummaryArray);
    }

    public List<ContainerSummary> getFilteredContainers(DockerContainerFilters filters) {
        String containersEndpoint = this.endpointBuilder.getContainersInfoEndpoint();
        String filterJson = this.converter.toJson(filters);

        ContainerSummary[] containerSummaryArray = this.restTemplate.getForObject(containersEndpoint + "?filters={filters}", ContainerSummary[].class, filterJson);
        return Arrays.asList(containerSummaryArray);
    }

    public ContainerDetails getContainerDetailsById(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getContainerInfoByIdEndpoint(containerId);

        return this.restTemplate.getForObject(containerByIdEndpoint, ContainerDetails.class);
    }

    public String getContainerLogsById(String containerId, String follow, String stdOut, String stdErr, String since, String timestamps, String tail) {
        String containerLogEndpoint = this.endpointBuilder.getContainerLogByIdEndpoint(containerId);
        containerLogEndpoint += "follow=" + follow;
        containerLogEndpoint += "&stdout=" + stdOut;
        containerLogEndpoint += "&stderr=" + stdErr;
        containerLogEndpoint += "&since=" + since;
        containerLogEndpoint += "&timestamps=" + timestamps;
        containerLogEndpoint += "&tail=" + tail;
        System.out.println(containerLogEndpoint);
        return this.restTemplate.getForObject(containerLogEndpoint, String.class);
    }
}
