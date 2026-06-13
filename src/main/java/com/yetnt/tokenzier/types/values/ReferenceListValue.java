package com.yetnt.tokenzier.types.values;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.Tokenizer;
import com.yetnt.tokenzier.types.AtomicValue;
import com.yetnt.tokenzier.types.FormEntryValue;

import java.util.ArrayList;
import java.util.List;

public class ReferenceListValue extends FormEntryValue<ArrayList<ReferToValue>> {
    public ReferenceListValue(String value) throws BureaucraticError {
        super(new ArrayList<>());
        String[] references = value.split(",");
        for (String ref : references) {
            String trimmedRef = ref.trim();
            FormEntryValue<?> val = Tokenizer.convertValue(trimmedRef);
            if (val instanceof ReferToValue referToValue) {
                getValue().add(referToValue);
            } else {
                throw new BureaucraticError("Invalid Reference Given");
            }
        }
    }

    public ReferenceListValue(ArrayList<ReferToValue> value) {
        super(value);
    }

    public List<ReferToValue> getReferences() {
        return getValue();
    }

}
