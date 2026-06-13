package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.FormEntryValue;
import com.yetnt.tokenzier.types.ListValue;

import java.util.ArrayList;

public class StringListValue extends FormEntryValue<ArrayList<StringValue>> implements ListValue<StringValue> {
    public StringListValue(String value) {
        super(new ArrayList<>());
        String[] values = value.split(",");
        for (String val : values) {
            getValue().add(new StringValue(val.trim()));
        }
    }

    public StringListValue(ArrayList<StringValue> value) {
        super(value);
    }

    @Override
    public ArrayList<StringValue> list() {
        return value;
    }
}
