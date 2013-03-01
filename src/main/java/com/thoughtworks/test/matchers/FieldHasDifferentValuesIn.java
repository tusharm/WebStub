package com.thoughtworks.test.matchers;

import org.apache.commons.collections.Predicate;

import java.lang.reflect.Field;

import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_DATES;

class FieldHasDifferentValuesIn implements Predicate {
    private Object actual;
    private Object expected;

    FieldHasDifferentValuesIn(Object expected, Object actual) {
        this.actual = actual;
        this.expected = expected;
    }

    @Override
    public boolean evaluate(Object o) {
        Field field = (Field) o;
        return !hasFieldValue(actual, field.getName(), fieldValue(expected, field));
    }

    private boolean hasFieldValue(Object object, String fieldName, Object fieldValue) {
        try {
            assertPropertyReflectionEquals(fieldName, fieldValue, object, LENIENT_DATES);
            return true;
        } catch (Throwable error) {
            return false;
        }
    }

    private Object fieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException("Unable to access field: " + field.getName());
        }
    }
}
