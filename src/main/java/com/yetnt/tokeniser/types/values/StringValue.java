package com.yetnt.tokeniser.types.values;

import com.yetnt.tokeniser.types.values.base.IAtomicValue;
import com.yetnt.tokeniser.types.values.base.BureaucraticType;
import com.yetnt.tokeniser.types.values.base.FormEntryValue;

/**
 * A token who is just a wrapper around Java's {@link String} type.
 * <p>
 *     This token is used to represent a string.
 * </p>
 * <p>
 *     Examples:
 *     <pre>{@code
 *     Form Title: Example
 *     Name: John Doe
 *     }</pre>
 * </p>
 * @see IAtomicValue
 * @see StringListValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "String")
public class StringValue extends FormEntryValue<String> implements IAtomicValue {
    public StringValue(String value) {
        super(value);
    }
}
