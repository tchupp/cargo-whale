package com.cargowhale.division.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.parseMediaType;

public class MediaTypeMatcher extends TypeSafeMatcher<String> {

    static Matcher<String> isCompatibleMediaType(final MediaType expected) {
        return new MediaTypeMatcher(expected);
    }

    private final MediaType expected;

    private MediaTypeMatcher(final MediaType expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(final String actual) {
        MediaType mediaType = parseMediaType(actual);
        return mediaType.isCompatibleWith(this.expected);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("Media type compatible with ").appendValue(this.expected);
    }

    @Override
    protected void describeMismatchSafely(final String actual, final Description mismatch) {
        if (actual == null) {
            mismatch.appendText("Media type was null");
        } else {
            mismatch.appendValue(actual);
        }
    }
}
