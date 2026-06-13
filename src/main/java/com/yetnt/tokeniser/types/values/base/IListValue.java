package com.yetnt.tokeniser.types.values.base;

import com.yetnt.tokeniser.types.values.EnumListValue;
import com.yetnt.tokeniser.types.values.ReferenceListValue;
import com.yetnt.tokeniser.types.values.StringListValue;

import java.util.ArrayList;

/**
 * Any value who is not itself a single value but a list of values. This is the marker interface for
 * {@link EnumListValue}, {@link ReferenceListValue} and {@link StringListValue}. In future IF, I happen to include more lists
 * then this will come in handy, otherwise both those classes are in reality just a list of strings.
 * @implNote All lists hold some form of {@link FormEntryValue} and never a raw Java type.
 * @see EnumListValue
 * @see StringListValue
 * @author Lehlogonolo Poole
 * @param <T> The type of the list
 */
@BureaucraticType(friendlyName = "[list value]")
public interface IListValue<T> {
    /**
     * Returns the underlying list. This is identical to calling {@link FormEntryValue#getValue()}
     * @return The underlying list
     */
    ArrayList<T> list();
}
