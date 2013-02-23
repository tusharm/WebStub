package com.thoughtworks.test.matchers;

import com.cedarsoftware.util.DeepEquals;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 *  Copyright 2013 ThoughtWorks Ltd
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
public class DeepEqualsMatcher<T> extends TypeSafeMatcher<T> {

    private DeepEquals deepEquals;

    public static <T> Matcher<T> deepEquals(T expected) {
        return new DeepEqualsMatcher<T>(expected);
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
        deepEquals = new DeepEquals();
    }

    public DeepEqualsMatcher(T expected) {
        this(expected, Ignored.NONE);
    }

    @Override
    protected boolean matchesSafely(T actual) {
        return deepEquals.deepEquals(expected, actual, ignoredFieldNames);
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
