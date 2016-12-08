package com.cargowhale.docker.container.info.integration;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.config.CargoWhaleProperties;
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

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static com.cargowhale.docker.test.ControllerTestUtils.getForType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerInfoControllerFilteredContainersIT {

    private static class ContainerSummaryIndexResourceType extends ParameterizedTypeReference<Resource<ContainerIndex>> {
    }

    @MockBean
    private DockerRestTemplate restTemplate;

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CargoWhaleProperties properties;

    @Test
    public void getFilteredContainers_Created() throws Exception {
        verifySingleStateFilter(ContainerState.CREATED);
    }

    @Test
    public void getFilteredContainers_Restarting() throws Exception {
        verifySingleStateFilter(ContainerState.RESTARTING);
    }

    @Test
    public void getFilteredContainers_Running() throws Exception {
        verifySingleStateFilter(ContainerState.RUNNING);
    }

    @Test
    public void getFilteredContainers_Paused() throws Exception {
        verifySingleStateFilter(ContainerState.PAUSED);
    }

    @Test
    public void getFilteredContainers_Exited() throws Exception {
        verifySingleStateFilter(ContainerState.EXITED);
    }

    @Test
    public void getFilteredContainers_Dead() throws Exception {
        verifySingleStateFilter(ContainerState.DEAD);
    }

    private void verifySingleStateFilter(final ContainerState containerState) throws URISyntaxException {
        ContainerSummary containerSummary = new ContainerSummary("test-id", Collections.singletonList("test-container1"), "test-image", "Exited (0) 9 days ago", containerState);
        ContainerSummary[] containerSummaryArray = Arrays.array(containerSummary);

        when(this.restTemplate.getForObject("/v1.24/containers/json?filters={filters}", ContainerSummary[].class, "{\"status\":[\"" + containerState.state + "\"]}"))
                .thenReturn(containerSummaryArray);

        ResponseEntity<Resource<ContainerIndex>> response = getForType(this.client, "/api/containers?state=" + containerState.state, new ContainerSummaryIndexResourceType());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerIndex containerIndex = response.getBody().getContent();
        List<ContainerSummary> containerSummaryList = containerIndex.getContainers();
        assertThat(containerSummaryList.size(), is(1));

        assertThat(containerSummaryList.get(0), equalTo(containerSummary));
    }

    @Test
    public void verifyBadFilterReturnsHttpBadRequest() {
        String state = "I_AM_A_TEAPOT";

        ResponseEntity<ContainerIndex> response = this.client.getForEntity("/api/containers?state=" + state, ContainerIndex.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}
