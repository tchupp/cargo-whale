package com.cargowhale.docker.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class MediaTypeMatcher extends TypeSafeMatcher<ResponseEntity<?>> {

    public static Matcher<ResponseEntity<?>> hasMediaType(final MediaType expected) {
        return new MediaTypeMatcher(expected);
    }

    private final MediaType expected;

    private MediaTypeMatcher(final MediaType expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(final ResponseEntity<?> response) {
        List<String> mediaTypes = response.getHeaders().get(HttpHeaders.CONTENT_TYPE);

        for (final String mediaTypeValue : mediaTypes) {
            if (mediaTypeValue.startsWith(this.expected.toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("to contain media type compatible with ").appendValue(this.expected);
    }

    @Override
    protected void describeMismatchSafely(final ResponseEntity<?> response, final Description mismatch) {
        if (response == null) {
            super.describeMismatch(null, mismatch);
        } else {
            List<String> mediaTypes = response.getHeaders().get(HttpHeaders.CONTENT_TYPE);
            mismatch.appendText("had ").appendValue(mediaTypes);
        }
    }
}
