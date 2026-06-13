package com.yetnt.tokenzier.types.values;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.types.EnumValues;
import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.FormEntryValue;
import com.yetnt.tokenzier.types.ListValue;

import java.util.ArrayList;

public class EnumListValue<T extends EnumValues> extends FormEntryValue<ArrayList<EnumValue<T>>> implements ListValue<EnumValue<T>> {
    private T enumValue;

    public EnumListValue(String value, T enumValue) {
        super(new ArrayList<>());
        this.enumValue = enumValue;
        String[] values = value.split(",");
        for (String val : values) {
            getValue().add(new EnumValue<>(val.trim(), (T) enumValue.getEnumValueFrom(val.trim())));
        }
    }

    public EnumListValue(ArrayList<EnumValue<T>> value, T enumValue) {
        super(value);
        this.enumValue = enumValue;
    }

    /**
     * gets the enum value. This isa  dummy value and doesnt represent the actual value held.
     * @return
     */
    public T getEnumValue() {
        return enumValue;
    }

    public void fromListStringValues(FormEntry<StringListValue> otherForm) throws BureaucraticError {
        StringListValue otherValue = otherForm.getValue();
        for (StringValue otherStringValue : otherValue.list()) {
            // if this fails, then not valid.
            T val = enumValue.getEnumValueFrom(otherStringValue.getValue());
            if (val == null) {
                throw new BureaucraticError(
                        "Form Entry does not expected the input \"" + otherStringValue.getValue() + "\""
                );
            }
            getValue().add(new EnumValue<>(otherStringValue.getValue(), val));
        }
    }

    @Override
    public ArrayList<EnumValue<T>> list() {
        return value;
    }
}
