package com.cargowhale.docker.controller;

import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.config.CargoWhaleProperties;
import com.cargowhale.docker.container.ContainerInfoCollectionVM;
import com.cargowhale.docker.container.ContainerInfoVM;
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

        ContainerInfoVM containerInfoVM1 = new ContainerInfoVM(Collections.singletonList("test-container1"), "test-image", containerState);
        ContainerInfoVM[] containerInfoVMs = Arrays.array(containerInfoVM1);

        DockerContainerFilters dockerContainerFilters = new DockerContainerFilters(Arrays.array(containerState));

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/json?filters={filters}", ContainerInfoVM[].class, dockerContainerFilters))
                .thenReturn(containerInfoVMs);

        ResponseEntity<ContainerInfoCollectionVM> response = this.client.getForEntity("/api/containers?state=" + containerState.state.toUpperCase(), ContainerInfoCollectionVM.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        ContainerInfoCollectionVM infoCollectionVM = response.getBody();
        List<ContainerInfoVM> containerInfoVMList = infoCollectionVM.getContainers();
        assertThat(containerInfoVMList.size(), is(1));

        assertThat(containerInfoVMList.get(0), equalTo(containerInfoVM1));
    }
}
