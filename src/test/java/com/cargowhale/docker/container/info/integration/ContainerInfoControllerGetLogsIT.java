package com.cargowhale.docker.container.info.integration;

import com.cargowhale.docker.config.CargoWhaleProperties;
import com.cargowhale.docker.container.info.model.ContainerLogs;
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
import org.springframework.web.client.RestTemplate;

import static com.cargowhale.docker.test.ControllerTestUtils.getForType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerInfoControllerGetLogsIT {

    private static class ContainerLogsResourceType extends ParameterizedTypeReference<Resource<ContainerLogs>> {
    }

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CargoWhaleProperties properties;

    @Test
    public void getLogsReturnsCorrectLogs() {
        String dockerUri = this.properties.getDockerUri();
        String container = "TestContainer";
        String logs = "These are definitely logs";
        String queries = "/logs?details=false&follow=false&stdout=true&stderr=true&timestamps=true&since=0&tail=100";

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/" + container + queries, String.class)).thenReturn(logs);

        ResponseEntity<Resource<ContainerLogs>> response = getForType(this.client, "/api/containers/" + container + queries, new ContainerLogsResourceType());
        verify(this.restTemplate).getForObject(dockerUri + "/v1.24/containers/" + container + queries, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        Resource<ContainerLogs> body = response.getBody();
        ContainerLogs logsResource = body.getContent();

        assertThat(logsResource.getLogs(), is(logs));
    }
}
