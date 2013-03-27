package com.thoughtworks.webstub.utils;

import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtils {
    public static <T> PredicatedPartition<T> partition(Collection<T> collection, Predicate predicate) {
        List<T> pre = new ArrayList<T>();
        List<T> post = new ArrayList<T>();
        for (T t : collection) {
            (predicate.evaluate(t) ? pre : post).add(t);
        }
        return new PredicatedPartition(pre, post);
    }

    public static <S, T> Collection<T> map(Collection<S> inputs, Mapper<S, T> mapper) {
        List<T> outputs = new ArrayList<T>();
        for (S input : inputs) {
            outputs.add(mapper.map(input));
        }

        return outputs;
    }
}
