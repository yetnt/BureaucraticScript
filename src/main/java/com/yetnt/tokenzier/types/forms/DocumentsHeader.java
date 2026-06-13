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

    public StringValue documentsTitle;
    public NumberValue version;
    public TimeValue time;
    public DateValue date;
    public NumberValue formsAmount;
    public StringValue author;
    public StringValue lineSeparator;

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
        documentsTitle = get(StringValue.class, formEntries.get("Documents Title"));
        version = get(NumberValue.class, formEntries.get("Version"));
        time = get(TimeValue.class, formEntries.get("Time"));
        date = get(DateValue.class, formEntries.get("Date"));
        formsAmount = get(NumberValue.class, formEntries.get("Forms Amount"));
        author = get(StringValue.class, formEntries.get("Author"));
        lineSeparator = get(StringValue.class, formEntries.get("Line Separator"));
    }
}
