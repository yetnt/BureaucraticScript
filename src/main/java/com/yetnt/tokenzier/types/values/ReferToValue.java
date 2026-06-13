package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.FormEntryValue;
import com.yetnt.tokenzier.types.ReferencialValue;

public class ReferToValue extends FormEntryValue<Integer> implements ReferencialValue {
    public ReferToValue(int value) {
        super(value);
    }

    public static String referenceText() {
        return "Refer To Form";
    }

    @Override
    public int getReferencedFormIndex() {
        return getValue();
    }
}
