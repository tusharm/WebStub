package com.thoughtworks.test.matchers;

import org.apache.commons.collections.Predicate;
import org.hamcrest.Description;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.exists;
import static org.apache.commons.collections.CollectionUtils.selectRejected;
import static org.unitils.reflectionassert.ReflectionAssert.assertPropertyReflectionEquals;

class DeepEqualsMatcherWithIgnores<T> extends DeepEqualsMatcher<T> {
    private List<String> ignoredFieldNames;

    DeepEqualsMatcherWithIgnores(T expected, List<String> ignoredFieldNames) {
        super(expected);
        this.ignoredFieldNames = ignoredFieldNames;
    }

    @Override
    protected boolean matchesSafely(T actual) {
        return !exists(unignoredFields(), new FieldHasDifferentValuesIn(getExpected(), actual));
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
}


