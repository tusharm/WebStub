package com.thoughtworks.test.utils;

public abstract class Predicate<T> implements org.apache.commons.collections.Predicate {
    public abstract boolean satisfies(T t);

    @Override
    public boolean evaluate(Object o) {
        return satisfies((T) o);
    }
}
