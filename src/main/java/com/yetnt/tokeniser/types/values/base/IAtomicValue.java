package com.yetnt.tokeniser.types.values.base;

import com.yetnt.tokeniser.types.FormEntry;
import com.yetnt.tokeniser.types.values.*;

/**
 * Any value of a {@link FormEntry} that resolves to a singular atomic value at interpret time.
 * @see IComputableValue
 * @see DateValue
 * @see TimeValue
 * @see ExpressionValue
 * @see StringValue
 * @see AsProvidedByValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "[Atomic Value]")
public interface IAtomicValue {
}
