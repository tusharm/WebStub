package com.thoughtworks.test.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class DeepEqualsMatcher<T> extends TypeSafeMatcher<T> {

    public static <T> Matcher<T> deepEquals(T expected) {
        return new DeepEqualsMatcher<T>(expected, Ignored.NONE);
    }

    public static <T> Matcher<T> deepEquals(T expected, Ignored ignoredFields) {
        return new DeepEqualsMatcher<T>(expected, ignoredFields);
    }

    public static Ignored ignoring(String... ignoredFields) {
        return new Ignored(ignoredFields);
    }

    private T expected;
    private List<String> ignoredFieldNames;

    private DeepEqualsMatcher(T expected, Ignored ignored) {
        this.expected = expected;
        this.ignoredFieldNames = ignored.names();
    }

    @Override
    protected boolean matchesSafely(T actual) {
        try {
            assertReflectionEquals(expected, actual);
            return true;
        } catch (Throwable error) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
        if (!ignoredFieldNames.isEmpty()) {
            description.appendText(" while ignoring fields: " + ignoredFieldNames);
        }
    }

    static class Ignored {
        private static Ignored NONE = new Ignored() {
            @Override
            List<String> names() {
                return emptyList();
            }
        };

        private List<String> ignored;

        private Ignored(String... ignoredNames) {
            this.ignored = asList(ignoredNames);
        }

        List<String> names() {
            return ignored;
        }
    }
}
