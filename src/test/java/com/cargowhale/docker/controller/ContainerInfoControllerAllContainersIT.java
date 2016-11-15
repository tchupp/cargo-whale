package com.cargowhale.docker.controller;

import com.cargowhale.docker.config.CargoWhaleProperties;
import com.cargowhale.docker.container.ContainerInfoCollection;
import com.cargowhale.docker.container.ContainerInfo;
import com.cargowhale.docker.container.ContainerState;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

        ContainerInfo[] containerInfoArray = Arrays.array();

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/json?all=1", ContainerInfo[].class)).thenReturn(containerInfoArray);

        ResponseEntity<ContainerInfoCollection> response = this.client.getForEntity("/api/containers", ContainerInfoCollection.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerInfoCollection containerInfoCollection = response.getBody();
        List<ContainerInfo> containerInfoList = containerInfoCollection.getContainers();
        assertThat(containerInfoList.size(), is(0));
    }

    @Test
    public void getAllContainers_OneContainers() {
        String dockerUri = this.properties.getDockerUri();

        ContainerInfo containerInfo1 = new ContainerInfo("hjf7y2nj1", Collections.singletonList("test-container1"), "test-image", ContainerState.CREATED);
        ContainerInfo[] containerInfoArray = Arrays.array(containerInfo1);

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/json?all=1", ContainerInfo[].class)).thenReturn(containerInfoArray);

        ResponseEntity<ContainerInfoCollection> response = this.client.getForEntity("/api/containers", ContainerInfoCollection.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerInfoCollection containerInfoCollection = response.getBody();
        List<ContainerInfo> containerInfoList = containerInfoCollection.getContainers();
        assertThat(containerInfoList.size(), is(1));

        assertThat(containerInfoList.get(0), equalTo(containerInfo1));
    }

    @Test
    public void getAllContainers_MultipleContainers() {
        String dockerUri = this.properties.getDockerUri();

        ContainerInfo containerInfo1 = new ContainerInfo("78nm12hb3", Collections.singletonList("test-container1"), "test-image", ContainerState.CREATED);
        ContainerInfo containerInfo2 = new ContainerInfo("nu91o2n3b", Collections.singletonList("test-container2"), "test-image", ContainerState.RUNNING);
        ContainerInfo[] containerInfoArray = Arrays.array(containerInfo1, containerInfo2);

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/json?all=1", ContainerInfo[].class)).thenReturn(containerInfoArray);

        ResponseEntity<ContainerInfoCollection> response = this.client.getForEntity("/api/containers", ContainerInfoCollection.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerInfoCollection containerInfoCollection = response.getBody();
        List<ContainerInfo> containerInfoList = containerInfoCollection.getContainers();
        assertThat(containerInfoList.size(), is(2));

        assertThat(containerInfoList.get(0), equalTo(containerInfo1));
        assertThat(containerInfoList.get(1), equalTo(containerInfo2));
    }
}
