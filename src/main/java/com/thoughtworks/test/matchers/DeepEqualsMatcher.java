package com.thoughtworks.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

import static java.util.Arrays.asList;

public abstract class DeepEqualsMatcher<T> extends TypeSafeMatcher<T> {

    public static <T> Matcher<T> deepEquals(T expected) {
        return new DeepEqualsMatcherNoIgnores<T>(expected);
    }

    public static <T> Matcher<T> deepEquals(T expected, Ignored ignoredFields) {
        return new DeepEqualsMatcherWithIgnores<T>(expected, ignoredFields.names());
    }

    public static Ignored ignoring(String... ignoredFields) {
        return new Ignored(ignoredFields);
    }

    private T expected;

    protected DeepEqualsMatcher(T expected) {
        this.expected = expected;
    }

    protected T getExpected() {
        return expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }

    static class Ignored {
        private List<String> ignored;

        private Ignored(String... ignoredNames) {
            this.ignored = asList(ignoredNames);
        }

        List<String> names() {
            return ignored;
        }
    }
}
