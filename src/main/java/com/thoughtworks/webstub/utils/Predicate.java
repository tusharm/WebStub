package com.thoughtworks.webstub.utils;

public abstract class Predicate<T> implements org.apache.commons.collections.Predicate {
    public abstract boolean satisfies(T t);

    @Override
    public final boolean evaluate(Object o) {
        return satisfies((T) o);
    }
}
