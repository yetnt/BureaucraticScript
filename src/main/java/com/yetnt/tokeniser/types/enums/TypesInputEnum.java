package com.yetnt.tokeniser.types.enums;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.forms.FunctionParamDefForm;
import com.yetnt.tokeniser.types.values.base.EnumValues;

import java.util.ArrayList;

public enum TypesInputEnum implements EnumValues {
    NUMBER("Number"),
    STRING("String"),
    BOOLEAN("Boolean"),
    DATE("Date"),
    TIME("Time"),
    REFERENCE("Reference"),
    ANY("Any");

    private final String value;

    TypesInputEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public ArrayList<String> getAcceptedValues() {
        ArrayList<String> acceptedValues = new ArrayList<>();
        for (TypesInputEnum expectedTypeEnum : TypesInputEnum.values()) {
            acceptedValues.add(expectedTypeEnum.value);
        }
        return acceptedValues;
    }

    @Override
    public <T extends EnumValues> T getEnumValueFrom(String otherValue, int lineNumber) throws BureaucraticError {
        return (T) switch (otherValue) {
            case "Number" -> TypesInputEnum.NUMBER;
            case "String" -> TypesInputEnum.STRING;
            case "Boolean" -> TypesInputEnum.BOOLEAN;
            case "Date" -> TypesInputEnum.DATE;
            case "Time" -> TypesInputEnum.TIME;
            case "Reference" -> TypesInputEnum.REFERENCE;
            case "Any" -> TypesInputEnum.ANY;
            default -> throw new BureaucraticError(
                    "Expected Type does not accept the following input given: \"" + otherValue + "\"",
                    lineNumber
            );
        };
    }
}
