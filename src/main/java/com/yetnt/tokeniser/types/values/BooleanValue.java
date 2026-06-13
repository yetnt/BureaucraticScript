package com.yetnt.tokeniser.types.values;

import com.yetnt.tokeniser.types.values.base.BureaucraticType;
import com.yetnt.tokeniser.types.values.base.FormEntryValue;
import com.yetnt.tokeniser.types.values.base.IComputableValue;

/**
 * A token who is just a wrapper around Java's {@link Boolean} type.
 * <p>
 *     Unlike other languages, This one defines a boolean to be a strict "True" or "False"
 *     property. 100% Case-sensitive.
 * </p>
 * <p>
 *     Exmaples:
 *     <pre>{@code
 *     Form Title: Example
 *     Optional: True
 *     Expression: True && False
 *     }</pre>
 * </p>
 * @see IComputableValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "Boolean")
public class BooleanValue extends FormEntryValue<Boolean> implements IComputableValue {
    public BooleanValue(boolean value) {
        super(value);
    }
}
