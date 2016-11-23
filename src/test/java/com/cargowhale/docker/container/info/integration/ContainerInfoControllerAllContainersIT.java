package com.cargowhale.docker.container.info.integration;

import com.cargowhale.docker.config.CargoWhaleProperties;
import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.container.info.model.ContainerSummaryIndex;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerInfoControllerAllContainersIT {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CargoWhaleProperties properties;

    @Test
    public void getAllContainers_NoContainers() {
        String dockerUri = this.properties.getDockerUri();

        ContainerSummary[] containerSummaryArray = Arrays.array();

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/json?all=1", ContainerSummary[].class)).thenReturn(containerSummaryArray);

        ResponseEntity<Resource<ContainerSummaryIndex>> response = exchange("/api/containers");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerSummaryIndex containerSummaryIndex = response.getBody().getContent();
        List<ContainerSummary> containerSummaryList = containerSummaryIndex.getContainers();
        assertThat(containerSummaryList.size(), is(0));
    }

    @Test
    public void getAllContainers_OneContainers() {
        String dockerUri = this.properties.getDockerUri();

        ContainerSummary containerSummary1 = new ContainerSummary("hjf7y2nj1", Collections.singletonList("test-container1"), "test-image", ContainerState.CREATED);
        ContainerSummary[] containerSummaryArray = Arrays.array(containerSummary1);

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/json?all=1", ContainerSummary[].class)).thenReturn(containerSummaryArray);

        ResponseEntity<Resource<ContainerSummaryIndex>> response = exchange("/api/containers");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerSummaryIndex containerSummaryIndex = response.getBody().getContent();
        List<ContainerSummary> containerSummaryList = containerSummaryIndex.getContainers();
        assertThat(containerSummaryList.size(), is(1));

        assertThat(containerSummaryList.get(0), is(containerSummary1));
    }

    @Test
    public void getAllContainers_MultipleContainers() {
        String dockerUri = this.properties.getDockerUri();

        ContainerSummary containerSummary1 = new ContainerSummary("78nm12hb3", Collections.singletonList("test-container1"), "test-image", ContainerState.CREATED);
        ContainerSummary containerSummary2 = new ContainerSummary("nu91o2n3b", Collections.singletonList("test-container2"), "test-image", ContainerState.RUNNING);
        ContainerSummary[] containerSummaryArray = Arrays.array(containerSummary1, containerSummary2);

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/json?all=1", ContainerSummary[].class)).thenReturn(containerSummaryArray);

        ResponseEntity<Resource<ContainerSummaryIndex>> response = exchange("/api/containers");

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerSummaryIndex containerSummaryIndex = response.getBody().getContent();
        List<ContainerSummary> containerSummaryList = containerSummaryIndex.getContainers();
        assertThat(containerSummaryList.size(), is(2));

        assertThat(containerSummaryList.get(0), equalTo(containerSummary1));
        assertThat(containerSummaryList.get(1), equalTo(containerSummary2));
    }

    private ResponseEntity<Resource<ContainerSummaryIndex>> exchange(final String url) {
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<Resource<ContainerSummaryIndex>> typeReference = new ParameterizedTypeReference<Resource<ContainerSummaryIndex>>() {
        };

        return this.client.exchange(url, HttpMethod.GET, requestEntity, typeReference, Collections.emptyMap());
    }
}
