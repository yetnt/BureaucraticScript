package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.AtomicValue;
import com.yetnt.tokenzier.types.FormEntryValue;

public class NumberValue extends FormEntryValue<Number> implements AtomicValue {
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
