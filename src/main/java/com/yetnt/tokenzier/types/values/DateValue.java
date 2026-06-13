package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.AtomicValue;
import com.yetnt.tokenzier.types.FormEntryValue;

import java.time.LocalDate;

public class DateValue extends FormEntryValue<LocalDate> implements AtomicValue {
    public DateValue(String value) {
        super(LocalDate.parse(value, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public DateValue(LocalDate value) {
        super(value);
    }

}
