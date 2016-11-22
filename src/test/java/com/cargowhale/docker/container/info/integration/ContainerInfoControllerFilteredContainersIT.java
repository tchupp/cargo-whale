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
public class ContainerInfoControllerFilteredContainersIT {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CargoWhaleProperties properties;

    @Test
    public void getFilteredContainers_Created() {
        verifySingleStateFilter(ContainerState.CREATED);
    }

    @Test
    public void getFilteredContainers_Restarting() {
        verifySingleStateFilter(ContainerState.RESTARTING);
    }

    @Test
    public void getFilteredContainers_Running() {
        verifySingleStateFilter(ContainerState.RUNNING);
    }

    @Test
    public void getFilteredContainers_Paused() {
        verifySingleStateFilter(ContainerState.PAUSED);
    }

    @Test
    public void getFilteredContainers_Exited() {
        verifySingleStateFilter(ContainerState.EXITED);
    }

    @Test
    public void getFilteredContainers_Dead() {
        verifySingleStateFilter(ContainerState.DEAD);
    }

    private void verifySingleStateFilter(final ContainerState containerState) {
        String dockerUri = this.properties.getDockerUri();

        ContainerSummary containerSummary1 = new ContainerSummary("test-id", Collections.singletonList("test-container1"), "test-image", containerState);
        ContainerSummary[] containerSummaryArray = Arrays.array(containerSummary1);

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/json?filters={filters}", ContainerSummary[].class, "{\"status\":[\"" + containerState.state + "\"]}"))
                .thenReturn(containerSummaryArray);

        ResponseEntity<ContainerSummaryIndex> response = this.client.getForEntity("/api/containers?state=" + containerState.state, ContainerSummaryIndex.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerSummaryIndex containerSummaryIndex = response.getBody();
        List<ContainerSummary> containerSummaryList = containerSummaryIndex.getContainers();
        assertThat(containerSummaryList.size(), is(1));

        assertThat(containerSummaryList.get(0), equalTo(containerSummary1));
    }

    @Test
    public void verifyBadFilterReturnsHttpBadRequest() {
        String state = "I_AM_A_TEAPOT";

        ResponseEntity<ContainerSummaryIndex> response = this.client.getForEntity("/api/containers?state=" + state, ContainerSummaryIndex.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}
