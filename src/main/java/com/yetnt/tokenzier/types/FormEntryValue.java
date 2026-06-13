package com.yetnt.tokenzier.types;

public class FormEntryValue<T> {
    protected T value;

    public FormEntryValue(T value) {
        this.value = value;
    }

    public FormEntryValue() {}

    public T getValue() {
        return value;
    }

}
