package com.cargowhale.docker.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONObject;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class JsonMatcher extends TypeSafeMatcher<String> {

    public static Matcher<String> equalToJsonString(final String expected) {
        return new JsonMatcher(expected);
    }

    private final String expected;

    private JsonMatcher(final String expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(final String actual) {
        try {
            assertEquals(actual, this.expected, false);
            return true;
        } catch (AssertionError | Exception ae) {
            return false;
        }
    }

    @Override
    public void describeTo(final Description description) {
        description.appendValue(new JSONObject(this.expected).toString(2));
    }

    @Override
    protected void describeMismatchSafely(final String actual, final Description mismatch) {
        if (actual == null) {
            super.describeMismatch(null, mismatch);
        } else {
            mismatch.appendText("was ").appendValue(new JSONObject(actual).toString(2)).appendText("\n");
        }
    }
}
