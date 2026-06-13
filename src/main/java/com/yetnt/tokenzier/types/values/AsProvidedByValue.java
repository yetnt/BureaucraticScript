package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.FormEntryValue;
import com.yetnt.tokenzier.types.ReferencialValue;

public class AsProvidedByValue extends FormEntryValue<Integer> implements ReferencialValue {
    public AsProvidedByValue(int value) {
        super(value);
    }

    public static String referenceText() {
        return "As Provided By Form";
    }

    @Override
    public int getReferencedFormIndex() {
        return getValue();
    }
}
