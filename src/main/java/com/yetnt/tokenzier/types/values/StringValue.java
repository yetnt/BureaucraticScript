package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.AtomicValue;
import com.yetnt.tokenzier.types.FormEntryValue;

public class StringValue extends FormEntryValue<String> implements AtomicValue {
    public StringValue(String value) {
        super(value);
    }
}
