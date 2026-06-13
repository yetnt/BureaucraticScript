package com.yetnt.tokeniser.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.FormEntry;
import com.yetnt.tokeniser.types.FormType;
import com.yetnt.tokeniser.types.values.*;
import com.yetnt.tokeniser.types.values.base.EnumValues;

import java.util.ArrayList;

public class LicenseForm extends Form {

    public StringValue formTitle;
    public FormEntry<EnumValue<LicenseTypeEnum>> licenseTypeFormEntry = new FormEntry<>("License Type", new EnumValue<>("Generic", LicenseTypeEnum.GENERIC));
    public FormEntry<EnumListValue<LicensePropertiesEnum>> licensePropertiesFormEntry = new FormEntry<>("License Properties", new EnumListValue<>(LicensePropertiesEnum.NO_DISTRIBUTION));
    public EnumValue<LicenseTypeEnum> licenseType;
    public EnumListValue<LicensePropertiesEnum> licenseProperties;
    public DateValue licenseYear;
    public DateValue renewalYear;

    public LicenseForm() throws BureaucraticError {
        super("License", FormType.PROCESS);
        formEntryOrder.add("Form Title");
        formEntryOrder.add("License Type");
        formEntryOrder.add("License Properties");
        formEntryOrder.add("License Year");
        formEntryOrder.add("Renewal Year");
    }

    @Override
    public void finish() throws BureaucraticError {
        super.finish();
        this.formType = FormType.LICENSE;
        formTitle = get(StringValue.class, formEntries.get("Form Title"));
        licenseType = get(licenseTypeFormEntry, formEntries.get("License Type"));
        licenseProperties = get(licensePropertiesFormEntry, formEntries.get("License Properties"), true);
        licenseYear = get(DateValue.class, formEntries.get("License Year"));
        renewalYear = get(DateValue.class, formEntries.get("Renewal Year"));
    }


    public enum LicensePropertiesEnum implements EnumValues {
        NO_DISTRIBUTION("No Distribution"),
        DISTRIBUTABLE("Distributable"),
        NO_RIGHTS_RESERVED("No Rights Reserved"),
        ALL_RIGHTS_RESERVED("All Rights Reserved"),
        NO_WARRANTY("No Warranty"),
        WARRANTY("Warranty");;

        private final String value;

        LicensePropertiesEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
        @Override
        public ArrayList<String> getAcceptedValues() {
            ArrayList<String> acceptedValues = new ArrayList<>();
            for (LicensePropertiesEnum licensePropertiesEnum : LicensePropertiesEnum.values()) {
                acceptedValues.add(licensePropertiesEnum.value);
            }
            return acceptedValues;
        }

        @Override
        public <T extends EnumValues> T getEnumValueFrom(String otherValue, int lineNumber) throws BureaucraticError {
            return (T) switch (otherValue) {
                case "No Distribution" -> LicensePropertiesEnum.NO_DISTRIBUTION;
                case "No Rights Reserved" -> LicensePropertiesEnum.NO_RIGHTS_RESERVED;
                case "No Warranty" -> LicensePropertiesEnum.NO_WARRANTY;
                case "Distributable" -> LicensePropertiesEnum.DISTRIBUTABLE;
                case "All Rights Reserved" -> LicensePropertiesEnum.ALL_RIGHTS_RESERVED;
                case "Warranty" -> LicensePropertiesEnum.WARRANTY;
                default -> throw new BureaucraticError(
                        "License Properties does not accept the following input given: \"" + otherValue + "\"",
                        lineNumber
                );
            };
        }

    }
    public enum LicenseTypeEnum implements EnumValues {
        GENERIC("Generic"),
        PROPRIETARY("Proprietary");

        private final String value;

        LicenseTypeEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public ArrayList<String> getAcceptedValues() {
            ArrayList<String> acceptedValues = new ArrayList<>();
            for (LicenseTypeEnum licenseTypeEnum : LicenseTypeEnum.values()) {
                acceptedValues.add(licenseTypeEnum.value);
            }
            return acceptedValues;
        }

        @Override
        public <T extends EnumValues> T getEnumValueFrom(String otherValue, int lineNumber) throws BureaucraticError {
            return (T) switch (otherValue) {
                case "Generic" -> LicenseTypeEnum.GENERIC;
                case "Proprietary" -> LicenseTypeEnum.PROPRIETARY;
                default -> throw new BureaucraticError(
                        "License Type does not expected the input \"" + otherValue + "\"",
                        lineNumber
                );
            };
        }
    }
}
