package com.yetnt.tokenzier.types;

import java.util.ArrayList;

public interface EnumValues {
    String getValue();
    default boolean isValid(String input) {
        return getAcceptedValues().contains(input);
    };
    ArrayList<String> getAcceptedValues();

    <T extends EnumValues> T getEnumValueFrom(String otherValue);
}
