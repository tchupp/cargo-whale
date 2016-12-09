package com.cargowhale.docker.client.containers.info.list;

import com.cargowhale.docker.client.containers.ContainerState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.MultiValueMap;

import java.util.LinkedHashSet;

import static org.assertj.core.util.Sets.newLinkedHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ListContainerFiltersTest {

    @Test
    public void queryParameters_OneOfEach() throws Exception {
        String containerFiltersJson = "{\"status\":[\"created\",\"running\"]}";
        LinkedHashSet<ContainerState> stateSet = newLinkedHashSet(ContainerState.CREATED, ContainerState.RUNNING);

        ListContainerFilters listContainerFilters = new ListContainerFilters(stateSet);
        MultiValueMap<String, String> queryParameters = listContainerFilters.asQueryParameters();

        assertThat(queryParameters.getFirst("filters"), is(containerFiltersJson));
    }

    @Test
    public void queryParameters_StatusIsNotRepeated() throws Exception {
        String containerFiltersJson = "{\"status\":[\"created\"]}";
        LinkedHashSet<ContainerState> stateSet = newLinkedHashSet(ContainerState.CREATED, ContainerState.CREATED);

        ListContainerFilters listContainerFilters = new ListContainerFilters(stateSet);
        MultiValueMap<String, String> queryParameters = listContainerFilters.asQueryParameters();

        assertThat(queryParameters.getFirst("filters"), is(containerFiltersJson));
    }

    @Test
    public void queryParameters_EmptyArray() throws Exception {
        String containerFiltersJson = "{\"status\":[]}";
        LinkedHashSet<ContainerState> stateSet = newLinkedHashSet();

        ListContainerFilters listContainerFilters = new ListContainerFilters(stateSet);
        MultiValueMap<String, String> queryParameters = listContainerFilters.asQueryParameters();

        assertThat(queryParameters.getFirst("filters"), is(containerFiltersJson));
    }
}