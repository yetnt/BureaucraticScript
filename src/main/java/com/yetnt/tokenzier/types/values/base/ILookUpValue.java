package com.yetnt.tokenzier.types.values.base;

import com.yetnt.tokenzier.types.values.AsProvidedByValue;
import com.yetnt.tokenzier.types.values.ReferToValue;

/**
 * Any form value which should cause the interpreter to perform a look up to a previous form.
 * Either to reference it directly via {@link ReferToValue} or to retrieve the value via
 * {@link AsProvidedByValue}.
 * @see ReferToValue
 * @see AsProvidedByValue
 * @author Lehlogonolo Poole
 */
public interface ILookUpValue {
    /**
     * The index of the form to look up.
     * @return The index of the form to look up
     */
    int getReferencedFormIndex();

    /**
     * Static generator method to create the lookup value token.
     * @param input The given input
     * @return The lookup value token
     */
    static FormEntryValue<?> getLookUpToken(String input) {
        if (input.startsWith(AsProvidedByValue.referenceText())) {
            return
                    new AsProvidedByValue(
                            Integer.parseInt(
                                    input.substring(AsProvidedByValue.referenceText().length())
                            )
                    );
        } else if (input.startsWith(ReferToValue.referenceText())) {
            return
                    new ReferToValue(
                            Integer.parseInt(
                                    input.substring(ReferToValue.referenceText().length())
                            )
                    );
        }

        return null;
    }
}
