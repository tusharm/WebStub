package com.thoughtworks.test.matchers;

import org.junit.Ignore;
import org.junit.Test;

import static com.thoughtworks.test.matchers.DeepEqualsMatcher.deepEquals;
import static com.thoughtworks.test.matchers.DeepEqualsMatcher.ignoring;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class DeepEqualsMatcherTest {
    @Test
    public void shouldMatchPrimitivesAndTheirWrappers() {
        assertThat(2, deepEquals(2));
        assertThat(new Integer(2), deepEquals(new Integer(2)));
    }

    @Test
    public void shouldMatchStrings() {
        assertThat("macbook", deepEquals("macbook"));
        assertThat("macbook", not(deepEquals("MacBook")));
    }

    @Test
    public void shouldMatchArrays() {
        assertThat(new String[]{"lion", "tiger"}, deepEquals(new String[]{"lion", "tiger"}));
        assertThat(new EAddress[]{new EAddress("a@b.com")}, deepEquals(new EAddress[]{new EAddress("a@b.com")}));
        assertThat(new EAddress[]{new EAddress("a@b.com")}, not(deepEquals(new EAddress[]{new EAddress("a@c.com")})));
    }

    @Test
    public void shouldMatchIfObjectsHaveSameFieldValues() {
        EAddress actual = new EAddress("a@b.com");
        EAddress expected = new EAddress("a@b.com");
        assertThat(actual, deepEquals(expected));
    }

    @Test
    public void shouldMatchIfObjectsHaveSameValuesInNestedObjects() {
        Employee actual = new Employee("John", "john@b.com");
        Employee expected = new Employee("John", "john@b.com");
        assertThat(actual, deepEquals(expected));
    }

    @Test
    public void shouldNotMatchIfObjectsHaveDifferentValuesInNestedObjects() {
        Employee actual = new Employee("John", "john@foo.com");
        Employee expected = new Employee("John", "john@bar.com");
        assertThat(actual, not(deepEquals(expected)));
    }

    @Test
    public void shouldMatchIfObjectsHaveDifferentValuesOfExcludedFields() {
        Employee actual = new Employee("John", "john@foo.com");
        Employee expected = new Employee("John", "john@bar.com");
        assertThat(actual, deepEquals(expected, ignoring("emailAddress")));
    }
}
