package com.cargowhale.docker.client;

import com.cargowhale.docker.container.ContainerState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class DockerContainerFiltersTest {

    private JacksonTester<DockerContainerFilters> json;

    @Before
    public void setUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void serializeJson_OneOfEach() throws Exception {
        String containerFiltersJson = "{\"status\":[\"created\",\"running\"]}";
        ContainerState[] statusSet = Arrays.array(ContainerState.CREATED, ContainerState.RUNNING);

        DockerContainerFilters containerFilters = new DockerContainerFilters(statusSet);

        assertThat(this.json.write(containerFilters).getJson(), is(containerFiltersJson));
    }

    @Test
    public void serializeJson_StatusIsNotRepeated() throws Exception {
        String containerFiltersJson = "{\"status\":[\"created\"]}";
        ContainerState[] statusSet = Arrays.array(ContainerState.CREATED, ContainerState.CREATED);

        DockerContainerFilters containerFilters = new DockerContainerFilters(statusSet);

        assertThat(this.json.write(containerFilters).getJson(), is(containerFiltersJson));
    }

    @Test
    public void serializeJson_EmptyArray() throws Exception {
        String containerFiltersJson = "{\"status\":[]}";
        ContainerState[] statusSet = Arrays.array();

        DockerContainerFilters containerFilters = new DockerContainerFilters(statusSet);

        assertThat(this.json.write(containerFilters).getJson(), is(containerFiltersJson));
    }
}