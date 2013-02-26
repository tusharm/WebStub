package com.thoughtworks.test.matchers;

import org.apache.commons.collections.Predicate;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.commons.collections.CollectionUtils.exists;
import static org.apache.commons.collections.CollectionUtils.selectRejected;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyReflectionEquals;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_DATES;

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
    protected boolean matchesSafely(final T actual) {
        if (ignoredFieldNames.isEmpty()){
            return reflectionMatches(actual);
        }

        return !exists(unignoredFields(), new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                Field field = (Field) o;
                field.setAccessible(true);
                String fieldName = field.getName();
                try {
                    Object fieldValue = field.get(expected);
                    return !fieldReflectionMatches(fieldName, fieldValue, actual);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access field: " + fieldName);
                }
            }
        });
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
        if (!ignoredFieldNames.isEmpty()) {
            description.appendText(" while ignoring fields: " + ignoredFieldNames);
        }
    }

    private boolean reflectionMatches(T actual) {
        try {
            assertReflectionEquals(expected, actual);
            return true;
        } catch (Throwable error) {
            return false;
        }
    }

    private List<Field> unignoredFields() {
        List<Field> declaredFields = asList(expected.getClass().getDeclaredFields());
        return (List<Field>) selectRejected(declaredFields, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                Field field = (Field) o;
                return ignoredFieldNames.contains(field.getName());
            }
        });
    }

    private boolean fieldReflectionMatches(String fieldName, Object fieldValue, T actual) {
        try {
            assertPropertyReflectionEquals(fieldName, fieldValue, actual, LENIENT_DATES);
            return true;
        } catch (Throwable error) {
            return false;
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
