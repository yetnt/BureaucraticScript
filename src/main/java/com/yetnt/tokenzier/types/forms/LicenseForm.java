package com.yetnt.tokenzier.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.types.Form;
import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.FormType;
import com.yetnt.tokenzier.types.values.DateValue;
import com.yetnt.tokenzier.types.values.EnumValue;
import com.yetnt.tokenzier.types.values.EnumValues;
import com.yetnt.tokenzier.types.values.StringValue;

import java.util.ArrayList;

public class LicenseForm extends Form {

    public FormEntry<StringValue> formTitle;
    public FormEntry<EnumValue<LicenseTypeEnum>> licenseType = new FormEntry<>("License Type", new EnumValue<>("Generic", LicenseTypeEnum.GENERIC));
    public FormEntry<EnumValue<LicensePropertiesEnum>> licenseProperties = new FormEntry<>("License Properties", new EnumValue<>("No Distribution", LicensePropertiesEnum.NO_DISTRIBUTION));
    public FormEntry<DateValue> licenseYear;
    public FormEntry<DateValue> renewalYear;

    public LicenseForm() {
        super("License Form", FormType.LICENSE);
        formEntryOrder.add("Form Title");
        formEntryOrder.add("License Type");
        formEntryOrder.add("License Properties");
        formEntryOrder.add("License Year");
        formEntryOrder.add("Renewal Year");
    }

    @Override
    public void finish() throws BureaucraticError {
        super.finish();
        formTitle = (FormEntry<StringValue>) formEntries.get("Form Title");
        licenseType.getValue().fromStringValue((FormEntry<StringValue>) formEntries.get("License Type"));
        licenseProperties.getValue().fromStringValue((FormEntry<StringValue>) formEntries.get("License Properties"));
        licenseYear = (FormEntry<DateValue>) formEntries.get("License Year");
        renewalYear = (FormEntry<DateValue>) formEntries.get("Renewal Year");
    }


    public enum LicensePropertiesEnum implements EnumValues {
        NO_DISTRIBUTION("No Distribution"),
        NO_RIGHTS_RESERVED("No Rights Reserved"),
        NO_WARRANTY("No Warranty");

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
        public <T extends EnumValues> T getEnumValueFrom(String otherValue) {
            return switch (otherValue) {
                case "No Distribution" -> (T) LicensePropertiesEnum.NO_DISTRIBUTION;
                case "No Rights Reserved" -> (T) LicensePropertiesEnum.NO_RIGHTS_RESERVED;
                case "No Warranty" -> (T) LicensePropertiesEnum.NO_WARRANTY;
                default -> null;
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
        public <T extends EnumValues> T getEnumValueFrom(String otherValue) {
            return switch (otherValue) {
                case "Generic" -> (T) LicenseTypeEnum.GENERIC;
                case "Proprietary" -> (T) LicenseTypeEnum.PROPRIETARY;
                default -> null;
            };
        }
    }
}
