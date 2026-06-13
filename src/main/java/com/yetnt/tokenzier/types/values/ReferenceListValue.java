package com.yetnt.tokenzier.types.values;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.Tokenizer;
import com.yetnt.tokenzier.types.values.base.BureaucraticType;
import com.yetnt.tokenzier.types.values.base.FormEntryValue;
import com.yetnt.tokenzier.types.values.base.IListValue;

import java.util.ArrayList;
import java.util.List;

/**
 * A list token, who is specifically a list of {@link ReferToValue} and nothing else.
 * <p>
 *     This is used in cases where you need to reference multiple forms at once in a single
 *     property, like the future Symbol Addition Request Form's Symbol Definition, which if a function
 *     this list of references would point to a list of Parameeter Definition Forms
 *     <pre>{@code
 *     Form Title: Symbol Addition Request
 *     Symbol Defintion: Refer To Form 2, Refer To Form 3, Refer To Form 8
 *     }</pre>
 *     </p>
 * @see IListValue
 * @see ReferToValue
 * @author Lehlogonolo Poole
 * </p>
 */
@BureaucraticType(friendlyName = "Reference List")
public class ReferenceListValue extends FormEntryValue<ArrayList<ReferToValue>> implements IListValue<ReferToValue> {
    public ReferenceListValue(String value) throws BureaucraticError {
        super(new ArrayList<>());
        String[] references = value.split(",");
        for (String ref : references) {
            String trimmedRef = ref.trim();
            FormEntryValue<?> val = Tokenizer.convertValue(trimmedRef, lineNumber);
            if (val instanceof ReferToValue referToValue) {
                getValue().add(referToValue);
            } else {
                throw new BureaucraticError("Invalid Reference Given \"" + trimmedRef + "\"", lineNumber);
            }
        }
    }

    public ReferenceListValue(ArrayList<?> value) {
        super((ArrayList<ReferToValue>) value);
    }

    public List<ReferToValue> getReferences() {
        return getValue();
    }

    @Override
    public ArrayList<ReferToValue> list() {
        return value;
    }

}
