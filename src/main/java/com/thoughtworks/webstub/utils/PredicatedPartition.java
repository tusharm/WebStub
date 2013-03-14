package com.thoughtworks.webstub.utils;

import java.util.List;

public class PredicatedPartition<T> {
    private List<T> satisfying;
    private List<T> notSatisfying;

    public PredicatedPartition(List<T> satisfying, List<T> notSatisfying) {
        this.satisfying = satisfying;
        this.notSatisfying = notSatisfying;
    }

    public List<T> satisfying() {
        return satisfying;
    }

    public List<T> notSatisfying() {
        return notSatisfying;
    }
}
