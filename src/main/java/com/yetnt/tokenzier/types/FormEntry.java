package com.yetnt.tokenzier.types;

import com.yetnt.tokenzier.types.values.base.FormEntryValue;

/**
 * Represents a single entry within a {@link Form}, consisting of a key and a value.
 *
 * @param <T> The type of the {@link FormEntryValue} associated with this entry.
 * @author Lehlogonolo Poole
 */
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
