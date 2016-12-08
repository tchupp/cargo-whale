package com.cargowhale.docker.container.info.integration;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.info.model.ContainerIndex;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static com.cargowhale.docker.test.ControllerTestUtils.getForType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerInfoControllerAllContainersIT {

    private static class ContainerSummaryIndexResourceType extends ParameterizedTypeReference<Resource<ContainerIndex>> {
    }

    @MockBean
    private DockerRestTemplate restTemplate;

    @Autowired
    private TestRestTemplate client;

    @Test
    public void getAllContainers_NoContainers() {
        ContainerSummary[] containerSummaryArray = Arrays.array();

        when(this.restTemplate.getForObject("/v1.24/containers/json?all=1", ContainerSummary[].class)).thenReturn(containerSummaryArray);

        ResponseEntity<Resource<ContainerIndex>> response = getForType(this.client, "/api/containers", new ContainerSummaryIndexResourceType());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerIndex containerIndex = response.getBody().getContent();
        List<ContainerSummary> containerSummaryList = containerIndex.getContainers();
        assertThat(containerSummaryList.size(), is(0));
    }

    @Test
    public void getAllContainers_OneContainers() {
        ContainerSummary containerSummary1 = new ContainerSummary("hjf7y2nj1", Collections.singletonList("test-container1"), "test-image", "Created 4 hours ago", ContainerState.CREATED);
        ContainerSummary[] containerSummaryArray = Arrays.array(containerSummary1);

        when(this.restTemplate.getForObject("/v1.24/containers/json?all=1", ContainerSummary[].class)).thenReturn(containerSummaryArray);

        ResponseEntity<Resource<ContainerIndex>> response = getForType(this.client, "/api/containers", new ContainerSummaryIndexResourceType());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerIndex containerIndex = response.getBody().getContent();
        List<ContainerSummary> containerSummaryList = containerIndex.getContainers();
        assertThat(containerSummaryList.size(), is(1));

        assertThat(containerSummaryList.get(0), is(containerSummary1));
    }

    @Test
    public void getAllContainers_MultipleContainers() {
        ContainerSummary containerSummary1 = new ContainerSummary("78nm12hb3", Collections.singletonList("test-container1"), "test-image", "Created 3 days ago", ContainerState.CREATED);
        ContainerSummary containerSummary2 = new ContainerSummary("nu91o2n3b", Collections.singletonList("test-container2"), "test-image", "Up 6 days", ContainerState.RUNNING);
        ContainerSummary[] containerSummaryArray = Arrays.array(containerSummary1, containerSummary2);

        when(this.restTemplate.getForObject("/v1.24/containers/json?all=1", ContainerSummary[].class)).thenReturn(containerSummaryArray);

        ResponseEntity<Resource<ContainerIndex>> response = getForType(this.client, "/api/containers", new ContainerSummaryIndexResourceType());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerIndex containerIndex = response.getBody().getContent();
        List<ContainerSummary> containerSummaryList = containerIndex.getContainers();
        assertThat(containerSummaryList.size(), is(2));

        assertThat(containerSummaryList.get(0), equalTo(containerSummary1));
        assertThat(containerSummaryList.get(1), equalTo(containerSummary2));
    }
}
