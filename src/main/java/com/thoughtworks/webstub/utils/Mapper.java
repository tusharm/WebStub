package com.thoughtworks.webstub.utils;

public abstract class Mapper<S, T> implements org.apache.commons.collections.Transformer {
    public abstract T map(S source);

    @Override
    public final Object transform(Object o) {
        return map((S) o);
    }
}
