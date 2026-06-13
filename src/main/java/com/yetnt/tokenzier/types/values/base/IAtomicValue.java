package com.yetnt.tokenzier.types.values.base;

import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.values.*;

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
