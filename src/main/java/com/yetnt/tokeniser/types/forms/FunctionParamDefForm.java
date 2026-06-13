package com.yetnt.tokeniser.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.FormEntry;
import com.yetnt.tokeniser.types.FormType;
import com.yetnt.tokeniser.types.values.BooleanValue;
import com.yetnt.tokeniser.types.values.EnumValue;
import com.yetnt.tokeniser.types.values.StringValue;
import com.yetnt.tokeniser.types.values.base.Any;
import com.yetnt.tokeniser.types.values.base.EnumValues;
import com.yetnt.tokeniser.types.values.base.FormEntryValue;

import java.util.ArrayList;

public class FunctionParamDefForm extends Form {
    public FunctionParamDefForm() {
        super("Function Parameter Definition", FormType.PROCESS);
        formEntryOrder.add("Form Title");
        formEntryOrder.add("Parameter Name");
        formEntryOrder.add("Expected Type");
        formEntryOrder.add("Optional");
        formEntryOrder.add("Default Value");
    }

    public StringValue parameterName;
    public EnumValue<ExpectedTypeEnum> expectedType;
    public FormEntry<EnumValue<ExpectedTypeEnum>> expectedTypeFormEntry =
            new FormEntry<>("Expected Type", new EnumValue<>("Any", ExpectedTypeEnum.ANY));
    public BooleanValue optional;
    public Object defaultValue;

    @Override
    public void finish() throws BureaucraticError {
        super.finish();
        this.formType = FormType.GENERIC;
        parameterName = get(StringValue.class, formEntries.get("Parameter Name"));
        expectedType = get(expectedTypeFormEntry, formEntries.get("Expected Type"));
        optional = get(BooleanValue.class, formEntries.get("Optional"));
        defaultValue = getUntyped(Any.class, formEntries.get("Default Value"));
    }

    public enum ExpectedTypeEnum implements EnumValues {
        NUMBER("Number"),
        STRING("String"),
        BOOLEAN("Boolean"),
        DATE("Date"),
        TIME("Time"),
        REFERENCE("Reference"),
        ANY("Any");

        private final String value;

        ExpectedTypeEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public ArrayList<String> getAcceptedValues() {
            ArrayList<String> acceptedValues = new ArrayList<>();
            for (ExpectedTypeEnum expectedTypeEnum : ExpectedTypeEnum.values()) {
                acceptedValues.add(expectedTypeEnum.value);
            }
            return acceptedValues;
        }

        @Override
        public <T extends EnumValues> T getEnumValueFrom(String otherValue, int lineNumber) throws BureaucraticError {
            return (T) switch (otherValue) {
                case "Number" -> ExpectedTypeEnum.NUMBER;
                case "String" -> ExpectedTypeEnum.STRING;
                case "Boolean" -> ExpectedTypeEnum.BOOLEAN;
                case "Date" -> ExpectedTypeEnum.DATE;
                case "Time" -> ExpectedTypeEnum.TIME;
                case "Reference" -> ExpectedTypeEnum.REFERENCE;
                case "Any" -> ExpectedTypeEnum.ANY;
                default -> throw new BureaucraticError(
                        "Expected Type does not accept the following input given: \"" + otherValue + "\"",
                        lineNumber
                );
            };
        }
    }


}
