package com.thoughtworks.webstub.utils;

import java.util.ArrayList;
import java.util.Collection;

public class PredicatedPartition<T> {
    private Collection<T> satisfying;
    private Collection<T> notSatisfying;

    public PredicatedPartition(Collection<T> satisfying, Collection<T> notSatisfying) {
        this.satisfying = satisfying;
        this.notSatisfying = notSatisfying;
    }

    public Collection<T> satisfying() {
        return satisfying;
    }

    public Collection<T> notSatisfying() {
        return notSatisfying;
    }

    public static <T> PredicatedPartition<T> empty() {
        return new PredicatedPartition<T>(new ArrayList<T>(), new ArrayList<T>());
    }
}
