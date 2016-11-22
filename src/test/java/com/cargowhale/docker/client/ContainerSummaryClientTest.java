package com.cargowhale.docker.client;

import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.util.JsonConverter;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerSummaryClientTest {

    private static final String DOCKER_ENDPOINT = "http://this.is.docker:yo";

    @InjectMocks
    private ContainerInfoClient client;

    @Mock
    private RestTemplate template;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Mock
    private JsonConverter converter;

    @Test
    public void getAllContainersReturnsEveryContainerFromDockerApi() {
        final ContainerSummary[] containerSummaryArray = Arrays.array(mock(ContainerSummary.class));

        when(this.endpointBuilder.getContainersEndpoint()).thenReturn(DOCKER_ENDPOINT);
        when(this.template.getForObject(DOCKER_ENDPOINT + "?all=1", ContainerSummary[].class)).thenReturn(containerSummaryArray);

        assertThat(this.client.getAllContainers(), contains(containerSummaryArray));
    }

    @Test
    public void getFilteredContainersReturnsSelectedTypesOfContainers() {
        String filterJson = "json filter string";

        DockerContainerFilters filters = mock(DockerContainerFilters.class);
        final ContainerSummary[] containerSummaryArray = Arrays.array(mock(ContainerSummary.class));

        when(this.endpointBuilder.getContainersEndpoint()).thenReturn(DOCKER_ENDPOINT);
        when(this.converter.toJson(filters)).thenReturn(filterJson);
        when(this.template.getForObject(DOCKER_ENDPOINT + "?filters={filters}", ContainerSummary[].class, filterJson))
                .thenReturn(containerSummaryArray);

        assertThat(this.client.getFilteredContainers(filters), contains(containerSummaryArray));
    }

    @Test
    public void getContainerByIdReturnsCorrectContainer() throws Exception {
        String containerId = "container id yo";
        ContainerDetails containerDetails = mock(ContainerDetails.class);

        when(this.endpointBuilder.getContainerByIdEndpoint(containerId)).thenReturn(DOCKER_ENDPOINT + containerId);
        when(this.template.getForObject(DOCKER_ENDPOINT + containerId, ContainerDetails.class)).thenReturn(containerDetails);

        assertThat(this.client.getContainerDetailsById(containerId), is(containerDetails));
    }
}

