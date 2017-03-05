package com.cargowhale.division.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONException;
import org.json.JSONObject;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class JsonMatcher extends TypeSafeMatcher<String> {

    static Matcher<String> equalToJsonString(final String expected) {
        return new JsonMatcher(expected);
    }

    private final String expected;

    private JsonMatcher(final String expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(final String actual) {
        try {
            assertEquals(actual, this.expected, true);
            return true;
        } catch (AssertionError | Exception ae) {
            return false;
        }
    }

    @Override
    public void describeTo(final Description description) {
        String jsonString = this.expected;
        try {
            jsonString = new JSONObject(this.expected).toString(2);
        } catch (final JSONException ignored) {
        }
        description.appendValue(jsonString);
    }

    @Override
    protected void describeMismatchSafely(final String actual, final Description mismatch) {
        if (actual == null) {
            super.describeMismatch(null, mismatch);
        } else {
            String jsonString = actual;
            try {
                jsonString = new JSONObject(actual).toString(2);
            } catch (final JSONException ignored) {
            }
            mismatch.appendText("was ").appendValue(jsonString).appendText("\n");
        }
    }
}
