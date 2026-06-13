package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.values.base.BureaucraticType;
import com.yetnt.tokenzier.types.values.base.FormEntryValue;
import com.yetnt.tokenzier.types.values.base.ILookUpValue;

/**
 * A look-up token who's job it is to reference another form. A token which cannot become
 * an atomic value as it attaches the reference to another form to this current {@link FormEntry}
 * during interpret time.
 * <p>
 *     This is usually in the form of:
 *     <pre>{@code
 *     Refer To Form X
 *     }</pre>
 *     In a form :
 *     <pre>{@code
 *     Form Title: Example
 *     Body: Refer To Form 2
 *     ~ or
 *     Body: (Refer To Form 2) ~ Using braces is preferred.
 *     }</pre>
 * </p>
 * @see ILookUpValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "Reference")
public class ReferToValue extends FormEntryValue<Integer> implements ILookUpValue {
    public ReferToValue(int value) {
        super(value);
    }

    public static String referenceText() {
        return "Refer To Form";
    }

    @Override
    public int getReferencedFormIndex() {
        return getValue();
    }
}
