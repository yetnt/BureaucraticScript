package com.yetnt.tokeniser.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.FormEntry;
import com.yetnt.tokeniser.types.FormType;
import com.yetnt.tokeniser.types.values.EnumValue;
import com.yetnt.tokeniser.types.values.ReferToValue;
import com.yetnt.tokeniser.types.values.StringValue;
import com.yetnt.tokeniser.types.values.base.EnumValues;

import java.util.ArrayList;

public class ScopeRegistrationForm extends Form {

    public StringValue formTitle;
    public ReferToValue scopingAuthority;
    public StringValue author;
    public FormEntry<EnumValue<ExecutionEnum>> exectuionFormEntry =
            new FormEntry<>("Execution", new EnumValue<>("One Time", ExecutionEnum.ONE_TIME));
    public FormEntry<EnumValue<SafetyEnum>> safetyFormEntry =
            new FormEntry<>("Safety", new EnumValue<>("Unsafe", SafetyEnum.UNSAFE));
    public EnumValue<ExecutionEnum> execution;
    public EnumValue<SafetyEnum> safety;

    public ScopeRegistrationForm() {
        super("Scope Registration Request", FormType.PROCESS);
        formEntryOrder.add("Form Title");
        formEntryOrder.add("Scoping Authority");
        formEntryOrder.add("Scope Author");
        formEntryOrder.add("Execution");
        formEntryOrder.add("Safety");
    }

    @Override
    public void finish() throws BureaucraticError {
        super.finish();
        this.formType = FormType.GENERIC;
        formTitle = get(StringValue.class, formEntries.get("Form Title"));
        scopingAuthority = get(ReferToValue.class, formEntries.get("Scoping Authority"));
        author = get(StringValue.class, formEntries.get("Scope Author"));
        execution = get(exectuionFormEntry, formEntries.get("Execution"));
        safety = get(safetyFormEntry, formEntries.get("Safety"));
    }

    public enum ExecutionEnum implements EnumValues {
        ONE_TIME("Once"),
        MULTIPLE_TIMES("Multiple Times");

        private final String value;

        ExecutionEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public ArrayList<String> getAcceptedValues() {
            ArrayList<String> acceptedValues = new ArrayList<>();
            for (ExecutionEnum executionEnum : ExecutionEnum.values()) {
                acceptedValues.add(executionEnum.value);
            }
            return acceptedValues;
        }

        @Override
        public <T extends EnumValues> T getEnumValueFrom(String otherValue, int lineNumber) throws BureaucraticError {
            return (T) switch (otherValue) {
                case "Once" -> ExecutionEnum.ONE_TIME;
                case "Multiple Times" -> ExecutionEnum.MULTIPLE_TIMES;
                default -> throw new BureaucraticError(
                        "Execution does not accept the following input given: \"" + otherValue + "\"",
                        lineNumber
                );
            };
        }
    }

    public enum SafetyEnum implements EnumValues {
        UNSAFE("Unsafe"),
        SAFE("Guaranteed");

        private final String value;

        SafetyEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public ArrayList<String> getAcceptedValues() {
            ArrayList<String> acceptedValues = new ArrayList<>();
            for (SafetyEnum safetyEnum : SafetyEnum.values()) {
                acceptedValues.add(safetyEnum.value);
            }
            return acceptedValues;
        }

        @Override
        public <T extends EnumValues> T getEnumValueFrom(String otherValue, int lineNumber) throws BureaucraticError {
            return (T) switch (otherValue) {
                case "Unsafe" -> SafetyEnum.UNSAFE;
                case "Guaranteed" -> SafetyEnum.SAFE;
                default -> throw new BureaucraticError(
                        "Safety does not accept the following input given: \"" + otherValue + "\"",
                        lineNumber
                );
            };
        }
    }


}
