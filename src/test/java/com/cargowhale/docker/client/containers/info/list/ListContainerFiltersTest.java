package com.cargowhale.docker.client.containers.info.list;

import com.cargowhale.docker.client.containers.ContainerState;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ListContainerFiltersTest {

    @Test
    public void serializeJson_OneOfEach() throws Exception {
        String containerFiltersJson = "{\"status\":[\"created\",\"running\"]}";
        ContainerState[] statusSet = Arrays.array(ContainerState.CREATED, ContainerState.RUNNING);

        ListContainerFilters listContainerFilters = new ListContainerFilters(statusSet);

        assertThat(listContainerFilters.toJson(), is(containerFiltersJson));
    }

    @Test
    public void serializeJson_StatusIsNotRepeated() throws Exception {
        String containerFiltersJson = "{\"status\":[\"created\"]}";
        ContainerState[] statusSet = Arrays.array(ContainerState.CREATED, ContainerState.CREATED);

        ListContainerFilters listContainerFilters = new ListContainerFilters(statusSet);

        assertThat(listContainerFilters.toJson(), is(containerFiltersJson));
    }

    @Test
    public void serializeJson_EmptyArray() throws Exception {
        String containerFiltersJson = "{\"status\":[]}";
        ContainerState[] statusSet = Arrays.array();

        ListContainerFilters listContainerFilters = new ListContainerFilters(statusSet);

        assertThat(listContainerFilters.toJson(), is(containerFiltersJson));
    }
}