package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.values.base.BureaucraticType;
import com.yetnt.tokenzier.types.values.base.FormEntryValue;
import com.yetnt.tokenzier.types.values.base.IListValue;

import java.util.ArrayList;

/**
 * A list token, who is specifically a list of {@link StringValue}.
 * <p>
 *     This is used in cases where you need to provide multiple string values in a single
 *     property.
 *     <pre>{@code
 *     Form Title: Example
 *     Names: John Doe, Jane Smith, Peter Jones
 *     Names: John Doe ~ Using a single value for a list will just make a singleton list.
 *     }</pre>
 * </p>
 * @see IListValue
 * @see StringValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "String List")
public class StringListValue extends FormEntryValue<ArrayList<StringValue>> implements IListValue<StringValue> {
    public StringListValue(String value) {
        super(new ArrayList<>());
        String[] values = value.split(",");
        for (String val : values) {
            getValue().add(new StringValue(val.trim()));
        }
    }

    public StringListValue(ArrayList<?> value) {
        super((ArrayList<StringValue>) value);
    }

    @Override
    public ArrayList<StringValue> list() {
        return value;
    }
}
