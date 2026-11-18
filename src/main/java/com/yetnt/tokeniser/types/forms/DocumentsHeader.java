package com.yetnt.tokeniser.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.FormEntry;
import com.yetnt.tokeniser.types.FormType;
import com.yetnt.tokeniser.types.values.*;
import com.yetnt.tokeniser.types.values.base.EnumValues;

import java.util.ArrayList;

public class DocumentsHeader extends Form {

    public StringValue documentsTitle;
    public NumberValue version;
    public TimeValue time;
    public DateValue date;
    public NumberValue formsAmount;
    public StringValue author;

    public EnumValue<LineEndingEnum> lineSeparator;
    public FormEntry<EnumValue<LineEndingEnum>> lineSeparatorFormEntry =
            new FormEntry<>("Line Separator", new EnumValue<>("CRLF", LineEndingEnum.CRLF));

    public DocumentsHeader() {
        super("Documents Header",  FormType.PROCESS);
        add("Documents Title", StringValue.class);
        add("Version", NumberValue.class);
        add("Time", TimeValue.class);
        add("Date", DateValue.class);
        add("Forms Amount", NumberValue.class);
        add("Author", StringValue.class);
        add("Line Separator", EnumValue.class, LineEndingEnum.class);
    }

    @Override
    public void finish() throws BureaucraticError {
        super.finish();
        this.formType = FormType.DOCUMENTS_HEADER;
        documentsTitle = get(StringValue.class, formEntries.get("Documents Title"));
        version = get(NumberValue.class, formEntries.get("Version"));
        time = get(TimeValue.class, formEntries.get("Time"));
        date = get(DateValue.class, formEntries.get("Date"));
        formsAmount = get(NumberValue.class, formEntries.get("Forms Amount"));
        author = get(StringValue.class, formEntries.get("Author"));
        lineSeparator = get(lineSeparatorFormEntry, formEntries.get("Line Separator"));
    }

    public enum LineEndingEnum implements EnumValues {
        LF("LF"),
        CRLF("CRLF");

        private final String value;

        LineEndingEnum(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public ArrayList<String> getAcceptedValues() {
            ArrayList<String> acceptedValues = new ArrayList<>();
            for (LineEndingEnum expectedTypeEnum : LineEndingEnum.values()) {
                acceptedValues.add(expectedTypeEnum.value);
            }
            return acceptedValues;
        }

        @Override
        public <T extends EnumValues> T getEnumValueFrom(String otherValue, int lineNumber) throws BureaucraticError {
            return (T) switch (otherValue) {
                case "CRLF" -> LineEndingEnum.CRLF;
                case "LF" -> LineEndingEnum.LF;
                default -> throw new BureaucraticError(
                        "Expected Type does not accept the following input given: \"" + otherValue + "\"",
                        lineNumber
                );
            };
        }
    }

}
