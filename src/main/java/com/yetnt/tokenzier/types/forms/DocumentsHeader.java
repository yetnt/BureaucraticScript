package com.yetnt.tokenzier.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.types.Form;
import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.FormType;
import com.yetnt.tokenzier.types.values.DateValue;
import com.yetnt.tokenzier.types.values.NumberValue;
import com.yetnt.tokenzier.types.values.StringValue;
import com.yetnt.tokenzier.types.values.TimeValue;

public class DocumentsHeader extends Form {

    public FormEntry<StringValue> documentsTitle;
    public FormEntry<NumberValue> version;
    public FormEntry<TimeValue> time;
    public FormEntry<DateValue> date;
    public FormEntry<NumberValue> formsAmount;
    public FormEntry<StringValue> author;
    public FormEntry<StringValue> lineSeparator;

    public DocumentsHeader() {
        super("Documents Header",  FormType.PROCESS);
        formEntryOrder.add("Documents Title");
        formEntryOrder.add("Version");
        formEntryOrder.add("Time");
        formEntryOrder.add("Date");
        formEntryOrder.add("Forms Amount");
        formEntryOrder.add("Author");
        formEntryOrder.add("Line Separator");
    }

    @Override
    public void finish() throws BureaucraticError {
        super.finish();
        this.formType = FormType.DOCUMENTS_HEADER;
        documentsTitle = (FormEntry<StringValue>) formEntries.get("Documents Title");
        version = (FormEntry<NumberValue>) formEntries.get("Version");
        time = (FormEntry<TimeValue>) formEntries.get("Time");
        date = (FormEntry<DateValue>) formEntries.get("Date");
        formsAmount = (FormEntry<NumberValue>) formEntries.get("Forms Amount");
        author = (FormEntry<StringValue>) formEntries.get("Author");
        lineSeparator = (FormEntry<StringValue>) formEntries.get("Line Separator");
    }
}
