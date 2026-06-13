package com.yetnt.tokeniser.types.values;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.values.base.BureaucraticType;
import com.yetnt.tokeniser.types.values.base.EnumValues;
import com.yetnt.tokeniser.types.FormEntry;
import com.yetnt.tokeniser.types.values.base.FormEntryValue;
import com.yetnt.tokeniser.types.values.base.IListValue;

import java.util.ArrayList;

/**
 * A list token, who is similar to {@link StringListValue} however it is restricted to a specific domain of
 * values via an enum. Similar to {@link EnumValue}
 * <p>
 *     This is usually in the form of:
 *     <pre>{@code
 *     Form Title:  Example
 *     Day       : Monday, Tuesday, Wednesday
 *     Day       : Monday ~ Using a single value for a list will just make a singleton list.
 *     }</pre>
 * </p>
 * @implSpec <p>
 *     The underlying {@link Form} which owns the {@link FormEntry} that holds this value, is responsible
 *     for creating the specific enum subclass with the allowed values and validation logic. This subclass
 *     must implement the {@link EnumValues} interface.
 * </p>
 * @see IListValue
 * @see EnumValue
 * @see EnumValues
 * @author Lehlogonolo Poole
 * @param <T> The enum type
 */
@BureaucraticType(friendlyName = "String List (Enum)")
public class EnumListValue<T extends EnumValues> extends FormEntryValue<ArrayList<EnumValue<T>>> implements IListValue<EnumValue<T>> {
    private T enumValue;

    public EnumListValue(T enumValue) throws BureaucraticError {
        super(new ArrayList<>());
        this.enumValue = enumValue;
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

    public void fromListStringValues(FormEntry<?> otherFormGeneric) throws BureaucraticError {
        attachLineNumber(otherFormGeneric.getValue().getLineNumber());
        if (otherFormGeneric.getValue() instanceof StringListValue otherForm) {
            // Given a form entry with a list of strings.
            for (StringValue otherStringValue : otherForm.list()) {
                // if this fails, then not valid.
                T val = enumValue.getEnumValueFrom(otherStringValue.getValue(), otherFormGeneric.getValue().getLineNumber());
                if (val == null) {
                    throw new BureaucraticError(
                            "Form Entry does not expected the input \"" + otherStringValue.getValue() + "\"",
                            otherFormGeneric.getValue().getLineNumber()
                    );
                }
                getValue().add(new EnumValue<>(otherStringValue.getValue(), val));
            }
        } else if (otherFormGeneric.getValue() instanceof StringValue sf) {
            // Given a form entry with a single string.
            T val = enumValue.getEnumValueFrom(sf.getValue(), sf.getLineNumber());
            getValue().add(
                    new EnumValue<>(sf.getValue(), val)
            );
        } else {
            throw new BureaucraticError(
                    "Form Entry does not expected the input \"" + otherFormGeneric.getValue().getValue() + "\"",
                    otherFormGeneric.getValue().getLineNumber()
            );
        }
    }

    @Override
    public ArrayList<EnumValue<T>> list() {
        return value;
    }
}
