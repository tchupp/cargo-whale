package com.cargowhale.docker.client.containers.info.logs;

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

        assertThat(queryParameters, hasKey("details"));
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

        assertThat(queryParameters.get("details"), contains(Arrays.array("false")));
        assertThat(queryParameters.get("follow"), contains(Arrays.array("false")));
        assertThat(queryParameters.get("stdout"), contains(Arrays.array("true")));
        assertThat(queryParameters.get("stderr"), contains(Arrays.array("true")));
        assertThat(queryParameters.get("timestamps"), contains(Arrays.array("true")));
        assertThat(queryParameters.get("since"), contains(Arrays.array("0")));
        assertThat(queryParameters.get("tail"), contains(Arrays.array("100")));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void asQueryParameters_CustomValues() throws Exception {
        Boolean details = false;
        Boolean follow = false;
        Boolean stdout = true;
        Boolean stderr = true;
        Boolean timestamps = true;
        Integer since = 12748918;
        String tail = "1934";

        LogFilters logFilters = new LogFilters(details, follow, stdout, stderr, timestamps, since, tail);

        MultiValueMap<String, String> queryParameters = logFilters.asQueryParameters();

        assertThat(queryParameters.get("details"), contains(Arrays.array(details.toString())));
        assertThat(queryParameters.get("follow"), contains(Arrays.array(follow.toString())));
        assertThat(queryParameters.get("stdout"), contains(Arrays.array(stdout.toString())));
        assertThat(queryParameters.get("stderr"), contains(Arrays.array(stderr.toString())));
        assertThat(queryParameters.get("timestamps"), contains(Arrays.array(timestamps.toString())));
        assertThat(queryParameters.get("since"), contains(Arrays.array(since.toString())));
        assertThat(queryParameters.get("tail"), contains(Arrays.array(tail)));


        details = true;
        follow = true;
        stdout = false;
        stderr = false;
        timestamps = true;
        since = 4781234;
        tail = "989";

        logFilters = new LogFilters(details, follow, stdout, stderr, timestamps, since, tail);

        queryParameters = logFilters.asQueryParameters();

        assertThat(queryParameters.get("details"), contains(Arrays.array(details.toString())));
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

        assertThat(queryParameters, hasKey("details"));
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

        assertThat(queryParameters.get("details"), is("false"));
        assertThat(queryParameters.get("follow"), is("false"));
        assertThat(queryParameters.get("stdout"), is("true"));
        assertThat(queryParameters.get("stderr"), is("true"));
        assertThat(queryParameters.get("timestamps"), is("true"));
        assertThat(queryParameters.get("since"), is("0"));
        assertThat(queryParameters.get("tail"), is("100"));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void asMap_CustomValues() throws Exception {
        Boolean details = false;
        Boolean follow = false;
        Boolean stdout = true;
        Boolean stderr = true;
        Boolean timestamps = true;
        Integer since = 12748918;
        String tail = "1934";

        LogFilters logFilters = new LogFilters(details, follow, stdout, stderr, timestamps, since, tail);

        Map<String, String> queryParameters = logFilters.asMap();

        assertThat(queryParameters.get("details"), is(details.toString()));
        assertThat(queryParameters.get("follow"), is(follow.toString()));
        assertThat(queryParameters.get("stdout"), is(stdout.toString()));
        assertThat(queryParameters.get("stderr"), is(stderr.toString()));
        assertThat(queryParameters.get("timestamps"), is(timestamps.toString()));
        assertThat(queryParameters.get("since"), is(since.toString()));
        assertThat(queryParameters.get("tail"), is(tail));


        details = true;
        follow = true;
        stdout = false;
        stderr = false;
        timestamps = true;
        since = 4781234;
        tail = "989";

        logFilters = new LogFilters(details, follow, stdout, stderr, timestamps, since, tail);

        queryParameters = logFilters.asMap();

        assertThat(queryParameters.get("details"), is(details.toString()));
        assertThat(queryParameters.get("follow"), is(follow.toString()));
        assertThat(queryParameters.get("stdout"), is(stdout.toString()));
        assertThat(queryParameters.get("stderr"), is(stderr.toString()));
        assertThat(queryParameters.get("timestamps"), is(timestamps.toString()));
        assertThat(queryParameters.get("since"), is(since.toString()));
        assertThat(queryParameters.get("tail"), is(tail));
    }
}