package com.thoughtworks.matchers;

import com.cedarsoftware.util.DeepEquals;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class DeepEqualsMatcher<T> extends TypeSafeMatcher<T> {

    private DeepEquals deepEquals;

    public static <T> Matcher<T> deepEquals(T expected) {
        return new DeepEqualsMatcher<T>(expected);
    }

    public static <T> Matcher<T> deepEquals(T expected, String... excludedFields) {
        return new DeepEqualsMatcher<T>(expected, asList(excludedFields));
    }

    private T expected;
    private List<String> excludedFieldNames;

    private DeepEqualsMatcher(T expected, List<String> excludedFieldNames) {
        this.expected = expected;
        this.excludedFieldNames = excludedFieldNames;
        deepEquals = new DeepEquals();
    }

    public DeepEqualsMatcher(T expected) {
        this(expected, Collections.<String>emptyList());
    }

    @Override
    protected boolean matchesSafely(T actual) {
        return deepEquals.deepEquals(expected, actual, excludedFieldNames);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
        if (!excludedFieldNames.isEmpty()) {
            description.appendText(" while ignoring fields: " + excludedFieldNames);
        }
    }
}
