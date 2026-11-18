package com.yetnt.tokeniser.types.values;

import com.yetnt.tokeniser.types.values.base.Any;
import com.yetnt.tokeniser.types.values.base.BureaucraticType;
import com.yetnt.tokeniser.types.values.base.FormEntryValue;
import com.yetnt.tokeniser.types.values.base.IListValue;

import java.util.ArrayList;
import java.util.List;

/**
 * A list token, who is specifically a list of {@link Any}.
 * <p>
 *     This is used in cases where you need to provide multiple of whatever value
 *     <pre>{@code
 *     Form Title: Example
 *     Parameters: John Doe, 1, Refer To Form 0
 *     Input: John Doe ~ Using a single value for a list will just make a singleton list.
 *     }</pre>
 * </p>
 * @see IListValue
 * @see Any
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "Any List")
public class AnyListValue extends FormEntryValue<ArrayList<Any>> implements IListValue<Any> {
//    public AnyListValue(String value) {
//        super(new ArrayList<>());
//        String[] values = value.split(",");
//        for (String val : values) {
//            getValue().add(new StringValue(val.trim()));
//        }
//    }

    public AnyListValue(ArrayList<?> value) {
        super((ArrayList<Any>) value);
    }
    public AnyListValue(Any value) {
        super(new ArrayList<>(List.of(value)));
    }

    @Override
    public ArrayList<Any> list() {
        return value;
    }
}
