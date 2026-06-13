package com.yetnt.tokenzier.types;

import com.yetnt.tokenzier.types.values.AsProvidedByValue;
import com.yetnt.tokenzier.types.values.ReferToValue;
import com.yetnt.tokenzier.types.values.StringValue;

public class FormEntry<T extends FormEntryValue<?>> {
    private final String key;
    private final T value;

    public FormEntry(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }

}
