package com.yetnt.tokenzier.types.values;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.FormEntryValue;

public class EnumValue<T extends EnumValues> extends FormEntryValue<String> {

    private T enumValue;

    public EnumValue(String value, T enumValue) {
        super(value);
        this.enumValue = enumValue;
    }

    public T getEnumValue() {
        return enumValue;
    }

    public void fromStringValue(FormEntry<StringValue> otherValueFormEntry) throws BureaucraticError {
        FormEntryValue<String> otherValue = otherValueFormEntry.getValue();
        if (enumValue.isValid(otherValue.getValue())) {
            this.value = otherValue.getValue();
            this.enumValue = enumValue.getEnumValueFrom(otherValue.getValue());
            return;
        }

        throw new BureaucraticError(
                "Form Entry does not expected the input \"" + otherValue.getValue() + "\""
        );
    }

}
