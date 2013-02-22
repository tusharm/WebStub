package com.thoughtworks.matchers;

import org.junit.Test;

import static com.thoughtworks.matchers.DeepEqualsMatcher.deepEquals;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class DeepEqualsMatcherTest {
    @Test
    public void shouldReturnTrueIfObjectsHaveSameFieldValues() {
        EAddress actual = new EAddress("a@b.com");
        EAddress expected = new EAddress("a@b.com");
        assertThat(actual, is(deepEquals(expected)));
    }

    @Test
    public void shouldReturnTrueIfObjectsHaveSameValuesInNestedObjects() {
        Employee actual = new Employee(0, "John", "john@b.com");
        Employee expected = new Employee(0, "John", "john@b.com");
        assertThat(actual, is(deepEquals(expected)));
    }

    @Test
    public void shouldReturnFalseIfObjectsHaveDifferentValuesInNestedObjects() {
        Employee actual = new Employee(0, "John", "john@b.com");
        Employee expected = new Employee(0, "John", "john@c.com");
        assertThat(actual, not(deepEquals(expected)));
    }

    @Test
    public void shouldReturnTrueIfObjectsHaveDifferentValuesOfExcludedFields() {
        Employee actual = new Employee(0, "John", "john@b.com");
        Employee expected = new Employee(0, "John", "john@c.com");
        assertThat(actual, deepEquals(expected, "emailAddress"));
    }
}
