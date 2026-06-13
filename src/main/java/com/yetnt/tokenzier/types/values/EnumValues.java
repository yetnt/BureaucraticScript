package com.yetnt.tokenzier.types.values;

import com.yetnt.errs.BureaucraticError;

import java.util.ArrayList;

public interface EnumValues {
    String getValue();
    default boolean isValid(String input) {
        return getAcceptedValues().contains(input);
    };
    ArrayList<String> getAcceptedValues();

    <T extends EnumValues> T getEnumValueFrom(String otherValue);
}
