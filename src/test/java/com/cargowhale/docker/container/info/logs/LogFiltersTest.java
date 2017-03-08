package com.cargowhale.docker.container.info.logs;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LogFiltersTest {

    @Test
    public void asQueryParameters_HasKeys() throws Exception {
        LogFilters logFilters = new LogFilters();

        MultiValueMap<String, String> queryParameters = logFilters.asQueryParameters();

        assertThat(queryParameters, hasKey("follow"));
        assertThat(queryParameters, hasKey("stdout"));
        assertThat(queryParameters, hasKey("stderr"));
        assertThat(queryParameters, hasKey("timestamps"));
        assertThat(queryParameters, hasKey("since"));
        assertThat(queryParameters, hasKey("tail"));
    }

    @Test
    public void asQueryParameters_DefaultValues() throws Exception {
        LogFilters logFilters = new LogFilters();

        MultiValueMap<String, String> queryParameters = logFilters.asQueryParameters();

        assertThat(queryParameters.get("follow"), contains(Arrays.array("false")));
        assertThat(queryParameters.get("stdout"), contains(Arrays.array("false")));
        assertThat(queryParameters.get("stderr"), contains(Arrays.array("false")));
        assertThat(queryParameters.get("timestamps"), contains(Arrays.array("false")));
        assertThat(queryParameters.get("since"), contains(Arrays.array("0")));
        assertThat(queryParameters.get("tail"), contains(Arrays.array("100")));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void asQueryParameters_CustomValues() throws Exception {
        Boolean follow = false;
        Boolean stdout = true;
        Boolean stderr = true;
        Boolean timestamps = true;
        Integer since = 12748918;
        String tail = "1934";

        LogFilters logFilters = new LogFilters(follow, stdout, stderr, timestamps, since, tail);

        MultiValueMap<String, String> queryParameters = logFilters.asQueryParameters();

        assertThat(queryParameters.get("follow"), contains(Arrays.array(follow.toString())));
        assertThat(queryParameters.get("stdout"), contains(Arrays.array(stdout.toString())));
        assertThat(queryParameters.get("stderr"), contains(Arrays.array(stderr.toString())));
        assertThat(queryParameters.get("timestamps"), contains(Arrays.array(timestamps.toString())));
        assertThat(queryParameters.get("since"), contains(Arrays.array(since.toString())));
        assertThat(queryParameters.get("tail"), contains(Arrays.array(tail)));


        follow = true;
        stdout = false;
        stderr = false;
        timestamps = true;
        since = 4781234;
        tail = "989";

        logFilters = new LogFilters(follow, stdout, stderr, timestamps, since, tail);

        queryParameters = logFilters.asQueryParameters();

        assertThat(queryParameters.get("follow"), contains(Arrays.array(follow.toString())));
        assertThat(queryParameters.get("stdout"), contains(Arrays.array(stdout.toString())));
        assertThat(queryParameters.get("stderr"), contains(Arrays.array(stderr.toString())));
        assertThat(queryParameters.get("timestamps"), contains(Arrays.array(timestamps.toString())));
        assertThat(queryParameters.get("since"), contains(Arrays.array(since.toString())));
        assertThat(queryParameters.get("tail"), contains(Arrays.array(tail)));
    }

    @Test
    public void asMap_HasKeys() throws Exception {
        LogFilters logFilters = new LogFilters();

        Map<String, String> queryParameters = logFilters.asMap();

        assertThat(queryParameters, hasKey("follow"));
        assertThat(queryParameters, hasKey("stdout"));
        assertThat(queryParameters, hasKey("stderr"));
        assertThat(queryParameters, hasKey("timestamps"));
        assertThat(queryParameters, hasKey("since"));
        assertThat(queryParameters, hasKey("tail"));
    }

    @Test
    public void asMap_DefaultValues() throws Exception {
        LogFilters logFilters = new LogFilters();

        Map<String, String> queryParameters = logFilters.asMap();

        assertThat(queryParameters.get("follow"), is("false"));
        assertThat(queryParameters.get("stdout"), is("false"));
        assertThat(queryParameters.get("stderr"), is("false"));
        assertThat(queryParameters.get("timestamps"), is("false"));
        assertThat(queryParameters.get("since"), is("0"));
        assertThat(queryParameters.get("tail"), is("100"));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void asMap_CustomValues() throws Exception {
        Boolean follow = false;
        Boolean stdout = true;
        Boolean stderr = true;
        Boolean timestamps = true;
        Integer since = 12748918;
        String tail = "1934";

        LogFilters logFilters = new LogFilters(follow, stdout, stderr, timestamps, since, tail);

        Map<String, String> queryParameters = logFilters.asMap();

        assertThat(queryParameters.get("follow"), is(follow.toString()));
        assertThat(queryParameters.get("stdout"), is(stdout.toString()));
        assertThat(queryParameters.get("stderr"), is(stderr.toString()));
        assertThat(queryParameters.get("timestamps"), is(timestamps.toString()));
        assertThat(queryParameters.get("since"), is(since.toString()));
        assertThat(queryParameters.get("tail"), is(tail));


        follow = true;
        stdout = false;
        stderr = false;
        timestamps = true;
        since = 4781234;
        tail = "989";

        logFilters = new LogFilters(follow, stdout, stderr, timestamps, since, tail);

        queryParameters = logFilters.asMap();

        assertThat(queryParameters.get("follow"), is(follow.toString()));
        assertThat(queryParameters.get("stdout"), is(stdout.toString()));
        assertThat(queryParameters.get("stderr"), is(stderr.toString()));
        assertThat(queryParameters.get("timestamps"), is(timestamps.toString()));
        assertThat(queryParameters.get("since"), is(since.toString()));
        assertThat(queryParameters.get("tail"), is(tail));
    }
}