package com.thoughtworks.test.matchers;

import org.apache.commons.collections.Predicate;
import org.hamcrest.Description;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.exists;
import static org.apache.commons.collections.CollectionUtils.selectRejected;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_DATES;

class DeepEqualsMatcherWithIgnores<T> extends DeepEqualsMatcher<T> {
    private List<String> ignoredFieldNames;

    DeepEqualsMatcherWithIgnores(T expected, List<String> ignoredFieldNames) {
        super(expected);
        this.ignoredFieldNames = ignoredFieldNames;
    }

    @Override
    protected boolean matchesSafely(T actual) {
        return !exists(unignoredFields(), new FieldDoesNotMatch(getExpected(), actual));
    }

    @Override
    public void describeTo(Description description) {
        super.describeTo(description);
        description.appendText(" while ignoring fields: " + ignoredFieldNames);
    }

    @SuppressWarnings("unchecked")
    private List<Field> unignoredFields() {
        List<Field> declaredFields = asList(getExpected().getClass().getDeclaredFields());
        return (List<Field>) selectRejected(declaredFields, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                Field field = (Field) o;
                return ignoredFieldNames.contains(field.getName());
            }
        });
    }

    class FieldDoesNotMatch implements Predicate {
        private T actual;
        private T expected;

        FieldDoesNotMatch(T expected, T actual) {
            this.actual = actual;
            this.expected = expected;
        }

        @Override
        public boolean evaluate(Object o) {
            Field field = (Field) o;
            String fieldName = field.getName();
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(expected);
                return !fieldReflectionMatches(fieldName, fieldValue, actual);
            } catch (Exception e) {
                throw new RuntimeException("Unable to access field: " + fieldName);
            }
        }

        private boolean fieldReflectionMatches(String fieldName, Object fieldValue, T actual) {
            try {
                assertPropertyReflectionEquals(fieldName, fieldValue, actual, LENIENT_DATES);
                return true;
            } catch (Throwable error) {
                return false;
            }
        }
    }
}


