package com.thoughtworks.test.matchers;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

class DeepEqualsMatcherNoIgnores<T> extends DeepEqualsMatcher<T> {
    DeepEqualsMatcherNoIgnores(T expected) {
        super(expected);
    }

    @Override
    protected boolean matchesSafely(T actual) {
        try {
            assertReflectionEquals(getExpected(), actual);
            return true;
        } catch (Throwable error) {
            return false;
        }
    }
}
