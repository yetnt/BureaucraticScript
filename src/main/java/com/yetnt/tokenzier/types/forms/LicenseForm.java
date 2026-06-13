package com.yetnt.tokenzier.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.types.Form;
import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.FormType;
import com.yetnt.tokenzier.types.values.*;
import com.yetnt.tokenzier.types.values.base.EnumValues;

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
            return switch (otherValue) {
                case "No Distribution" -> (T) LicensePropertiesEnum.NO_DISTRIBUTION;
                case "No Rights Reserved" -> (T) LicensePropertiesEnum.NO_RIGHTS_RESERVED;
                case "No Warranty" -> (T) LicensePropertiesEnum.NO_WARRANTY;
                case "Distributable" -> (T) LicensePropertiesEnum.DISTRIBUTABLE;
                case "All Rights Reserved" -> (T) LicensePropertiesEnum.ALL_RIGHTS_RESERVED;
                case "Warranty" -> (T) LicensePropertiesEnum.WARRANTY;
                default -> throw new BureaucraticError(
                        "Form Entry does not expected the input \"" + otherValue + "\"",
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
            return switch (otherValue) {
                case "Generic" -> (T) LicenseTypeEnum.GENERIC;
                case "Proprietary" -> (T) LicenseTypeEnum.PROPRIETARY;
                default -> throw new BureaucraticError(
                        "Form Entry does not expected the input \"" + otherValue + "\"",
                        lineNumber
                );
            };
        }
    }
}
