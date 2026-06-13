package com.yetnt.utils;

import java.util.Objects;

public class Pair<U, V> {
    private final U first;
    private final V second;

    public Pair(
            U first,
            V second
    ) {
        this.first = first;
        this.second = second;
    }

    public U first() {
        return first;
    }

    public V second() {
        return second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Pair) obj;
        return Objects.equals(this.first, that.first) &&
                Objects.equals(this.second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair[" +
                "first=" + first + ", " +
                "second=" + second + ']';
    }
};
