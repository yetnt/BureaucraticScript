package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.values.base.*;

/**
 * A look-up token who's job it is to retrieve the value that another form may generate.
 * Unlike a general {@link ILookUpValue} who is considered non-atomic, this class specifically
 * resolves to an atomic value and hence extends {@link IAtomicValue}
 * <p>
 *     This is usually in the form of:
 *     <pre>{@code
 *     As Provided By Form X
 *     }</pre>
 *     In a form and otherwise expression:
 *     <pre>{@code
 *     Form Title: Example
 *     Number: As Provided By Form 2
 *     Expression: (As Provided By Form 5) - 10
 *     }</pre>
 * </p>
 * @see ILookUpValue
 * @see IAtomicValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "Value Reference")
public class AsProvidedByValue extends FormEntryValue<Integer> implements ILookUpValue, IAtomicValue {
    public AsProvidedByValue(int value) {
        super(value);
    }

    public static String referenceText() {
        return "As Provided By Form";
    }

    @Override
    public int getReferencedFormIndex() {
        return getValue();
    }
}
