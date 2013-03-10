package com.thoughtworks.test.utils;

import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <T> Partition<T> split(List<T> collection, Predicate predicate) {
        List<T> pre = new ArrayList<T>();
        List<T> post = new ArrayList<T>();
        for (T t : collection) {
            (predicate.evaluate(t) ? pre : post).add(t);
        }
        return new Partition(pre, post);
    }
}
