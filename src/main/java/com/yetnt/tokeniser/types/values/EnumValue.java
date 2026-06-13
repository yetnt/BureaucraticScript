package com.yetnt.tokeniser.types.values;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.values.base.BureaucraticType;
import com.yetnt.tokeniser.types.values.base.EnumValues;
import com.yetnt.tokeniser.types.FormEntry;
import com.yetnt.tokeniser.types.values.base.FormEntryValue;
import com.yetnt.tokeniser.types.values.base.IAtomicValue;

/**
 * A token who is but similar to {@link StringValue} however it is restricted to a specific domain of
 * values via an enum. Similar to  {@link EnumListValue}
 * <p>
 *     This is usually in the form of:
 *     <pre>{@code
 *     Form Title: Example
 *     Day       : Monday
 *     }</pre>
 * </p>
 * @implSpec <p>
 *     The underlying {@link Form} which owns the {@link FormEntry} that holds this value, is responsible
 *     for creating the specific enum subclass with the allowed values and validation logic. This subclass
 *     must implement the {@link EnumValues} interface.
 * </p>
 * @see IAtomicValue
 * @see EnumListValue
 * @see EnumValues
 * @author Lehlogonolo Poole
 * @param <T> The enum type
 */
@BureaucraticType(friendlyName = "String (Enum)")
public class EnumValue<T extends EnumValues> extends FormEntryValue<String> implements IAtomicValue {

    private T enumValue;

    public EnumValue(String value, T enumValue) {
        super(value);
        this.enumValue = enumValue;
    }

    public T getEnumValue() {
        return enumValue;
    }

    public void fromStringValue(FormEntry<StringValue> otherValueFormEntry) throws BureaucraticError {
        attachLineNumber(otherValueFormEntry.getValue().getLineNumber());
        FormEntryValue<String> otherValue = otherValueFormEntry.getValue();
        if (enumValue.isValid(otherValue.getValue())) {
            this.value = otherValue.getValue();
            this.enumValue = enumValue.getEnumValueFrom(otherValue.getValue(), otherValue.getLineNumber());
            return;
        }

        throw new BureaucraticError(
                "Form Entry does not expected the input \"" + otherValue.getValue() + "\"",
                otherValueFormEntry.getValue().getLineNumber()
        );
    }

}
