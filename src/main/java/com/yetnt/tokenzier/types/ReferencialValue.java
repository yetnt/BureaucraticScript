package com.yetnt.tokenzier.types;

import com.yetnt.tokenzier.types.values.AsProvidedByValue;
import com.yetnt.tokenzier.types.values.ReferToValue;

/**
 * Form Values which reference others
 */
public interface ReferencialValue {
    int getReferencedFormIndex();

    static FormEntryValue<?> getReferencial(String input) {
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
