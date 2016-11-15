package com.cargowhale.docker.controller;

import com.cargowhale.docker.config.CargoWhaleProperties;
import com.cargowhale.docker.container.info.model.ContainerDetails;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerInfoControllerContainerByNameIT {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CargoWhaleProperties properties;

    @Test
    public void getContainerById() throws Exception {
        String containerId = "7vbk17823b";
        String dockerUri = this.properties.getDockerUri();

        ContainerDetails containerDetails = new ContainerDetails(containerId, "cool image");

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/" + containerId + "/json", ContainerDetails.class)).thenReturn(containerDetails);

        ResponseEntity<ContainerDetails> response = this.client.getForEntity("/api/containers/" + containerId, ContainerDetails.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(containerDetails));
    }
}
