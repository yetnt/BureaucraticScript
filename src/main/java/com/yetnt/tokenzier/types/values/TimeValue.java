package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.AtomicValue;
import com.yetnt.tokenzier.types.FormEntryValue;

import java.time.LocalTime;

public class TimeValue extends FormEntryValue<LocalTime> implements AtomicValue {
    public TimeValue(String value) {
        super(LocalTime.parse(value));
    }

    public TimeValue(LocalTime value) {
        super(value);
    }
}
