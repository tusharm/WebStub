package com.thoughtworks.webstub.utils;

import java.util.List;

public class Partition<T> {
    private List<T> left;
    private List<T> right;

    public Partition(List<T> left, List<T> right) {
        this.left = left;
        this.right = right;
    }

    public List<T> left() {
        return left;
    }

    public List<T> right() {
        return right;
    }
}
