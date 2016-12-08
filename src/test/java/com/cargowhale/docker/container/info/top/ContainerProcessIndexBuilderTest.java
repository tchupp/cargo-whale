package com.cargowhale.docker.container.info.top;

import com.cargowhale.docker.client.containers.info.top.ContainerTopResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ContainerProcessIndexBuilderTest {

    @InjectMocks
    private ContainerProcessIndexBuilder builder;

    @Test
    public void returnsProcessIndex_WithCorrectContainerId() throws Exception {
        String containerId = "contain_this";
        List<List<String>> processList = newArrayList();

        List<String> titles = newArrayList("PID", "USER", "TIME", "COMMAND");

        ContainerTopResponse containerTopResponse = new ContainerTopResponse(processList, titles);

        ContainerProcessIndex index = builder.buildProcessIndex(containerId, containerTopResponse);

        assertThat(index.getId(), is(containerId));
    }

    @Test
    public void returnsProcessIndex_NoProcesses() throws Exception {
        String containerId = "contain_this";
        List<List<String>> processList = newArrayList();

        List<String> titles = newArrayList("PID", "USER", "TIME", "COMMAND");

        ContainerTopResponse containerTopResponse = new ContainerTopResponse(processList, titles);

        ContainerProcessIndex index = builder.buildProcessIndex(containerId, containerTopResponse);

        List<Map<String, String>> processes = index.getProcesses();
        assertThat(processes, hasSize(0));
    }

    @Test
    public void returnsProcessIndex_OneProcesses() throws Exception {
        String containerId = "contain_this";
        List<String> process1 = newArrayList("10892", "root", "0:00", "/bin/sh -c echo");
        List<List<String>> processList = singletonList(process1);

        List<String> titles = newArrayList("PID", "USER", "TIME", "COMMAND");

        ContainerTopResponse containerTopResponse = new ContainerTopResponse(processList, titles);

        ContainerProcessIndex index = builder.buildProcessIndex(containerId, containerTopResponse);

        List<Map<String, String>> processes = index.getProcesses();
        assertThat(processes, hasSize(1));

        verifyProcessTitles(processes.get(0));

        verifyProcessValues(processes.get(0), process1);
    }

    @Test
    public void returnsProcessIndex_ManyProcesses() throws Exception {
        String containerId = "contain_this";
        List<String> process1 = newArrayList("10892", "root", "0:00", "/bin/sh -c echo");
        List<String> process2 = newArrayList("10932", "root", "0:00", "socat -v TCP-LISTEN:2375,reuseaddr,fork UNIX-CLIENT:/var/run/docker.sock");
        List<String> process3 = newArrayList("16227", "root", "0:00", "socat -v TCP-LISTEN:2375,reuseaddr,fork UNIX-CLIENT:/var/run/docker.sock");
        List<List<String>> processList = newArrayList(process1, process2, process3);

        List<String> titles = newArrayList("PID", "USER", "TIME", "COMMAND");

        ContainerTopResponse containerTopResponse = new ContainerTopResponse(processList, titles);

        ContainerProcessIndex index = builder.buildProcessIndex(containerId, containerTopResponse);

        List<Map<String, String>> processes = index.getProcesses();
        assertThat(processes, hasSize(3));

        verifyProcessTitles(processes.get(0));
        verifyProcessTitles(processes.get(1));
        verifyProcessTitles(processes.get(2));

        verifyProcessValues(processes.get(0), process1);
        verifyProcessValues(processes.get(1), process2);
        verifyProcessValues(processes.get(2), process3);
    }

    private static void verifyProcessTitles(final Map<String, String> actualProcess) {
        assertThat(actualProcess, hasKey("PID"));
        assertThat(actualProcess, hasKey("USER"));
        assertThat(actualProcess, hasKey("TIME"));
        assertThat(actualProcess, hasKey("COMMAND"));
    }

    private static void verifyProcessValues(final Map<String, String> actualProcess, final List<String> processValues) {
        assertThat(actualProcess.get("PID"), is(processValues.get(0)));
        assertThat(actualProcess.get("USER"), is(processValues.get(1)));
        assertThat(actualProcess.get("TIME"), is(processValues.get(2)));
        assertThat(actualProcess.get("COMMAND"), is(processValues.get(3)));
    }
}