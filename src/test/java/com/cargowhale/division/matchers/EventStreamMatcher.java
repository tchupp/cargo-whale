package com.cargowhale.division.matchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;

public class EventStreamMatcher extends TypeSafeMatcher<String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Matcher<String> eventStreamContains(final Object... objs) {
        return new EventStreamMatcher(newArrayList(objs));
    }

    private final List<Object> expected;

    private EventStreamMatcher(final List<Object> expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(final String actual) {
        boolean matches = true;
        for (final Object streamItem : newArrayList(this.expected)) {
            try {
                String streamItemString = OBJECT_MAPPER.writeValueAsString(streamItem);
                assertThat(actual, CoreMatchers.containsString(streamItemString));

                this.expected.remove(streamItem);
            } catch (AssertionError | JsonProcessingException ae) {
                matches = false;
            }
        }
        return matches;
    }

    @Override
    public void describeTo(final Description description) {
        Object[] expectedItems = Flowable.fromIterable(singletonList(this.expected))
            .map((value) -> {
                try {
                    return OBJECT_MAPPER.writeValueAsString(value);
                } catch (final JsonProcessingException ignored) {
                    return "";
                }
            })
            .toList()
            .blockingGet()
            .toArray();

        description.appendValue(expectedItems);
    }

    @Override
    protected void describeMismatchSafely(final String actual, final Description mismatch) {
        if (actual == null) {
            super.describeMismatch(null, mismatch);
        } else {
            String[] split = actual.split("\\n\\n");
            for (int i = 0; i < split.length; i++) {
                split[i] = split[i].replaceAll("data:", "").replaceAll("\\\\\"", "\"");
            }
            mismatch.appendText("contained ").appendValue(split).appendText("\n");
        }
    }
}
