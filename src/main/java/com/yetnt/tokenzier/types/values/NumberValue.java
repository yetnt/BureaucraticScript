package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.values.base.IAtomicValue;
import com.yetnt.tokenzier.types.values.base.BureaucraticType;
import com.yetnt.tokenzier.types.values.base.FormEntryValue;
import com.yetnt.tokenzier.types.values.base.IComputableValue;

/**
 * A token who is just a wrapper around Java's {@link Number} type.
 * <p>
 *     This token is used to represent a number, which can be either an integer or a double.
 * </p>
 * <p>
 *     Examples:
 *     <pre>{@code
 *     Form Title: Example
 *     Number: 123
 *     Decimal: 123.45
 *     Expression: 1 + 2.5
 *     }</pre>
 * </p>
 * @see IComputableValue
 * @see IAtomicValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "Number")
public class NumberValue extends FormEntryValue<Number> implements IComputableValue {
    public NumberValue(String value) throws NumberFormatException {
        super();
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            try {
                this.value = Double.parseDouble(value);
            } catch (NumberFormatException e2) {
                throw e2;
            }
        }
    }

    public NumberValue(int value) {
        super(value);
    }

    public NumberValue(double value) {
        super(value);
    }

    public int getIntValue() {
        return (int) getValue();
    }

    public double getDoubleValue() {
        return (double) getValue();
    }
}
