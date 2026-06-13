package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.values.base.BureaucraticType;
import com.yetnt.tokenzier.types.values.base.FormEntryValue;
import com.yetnt.tokenzier.types.values.base.IAtomicValue;
import com.yetnt.tokenzier.types.values.base.IListValue;

/**
 * A token who is just a wrapper around Java's {@link Void}
 * although, this is in the form of a string usually {@code N/A}
 * * <p>
 *     This token is used to represent a value that is not applicable.
 * </p>
 * <p>
 *     Examples:
 *     <pre>{@code
 *     Form Title: Example
 *     Reason: N/A
 *     }</pre>
 * </p>
 * @implSpec A {@link FormEntry} should never actually define or otherwise instill
 * default behaviour by using this. This is purely a value when the user does not want to enter information.
 * If this is used within any implementation of {@link IListValue} it will appear as a String.
 * and not this subclass.
 * @see IAtomicValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "Not Applicable")
public class NotApplicableValue extends FormEntryValue<Void> implements IAtomicValue {
    public NotApplicableValue() {
        super(null);
    }
}
