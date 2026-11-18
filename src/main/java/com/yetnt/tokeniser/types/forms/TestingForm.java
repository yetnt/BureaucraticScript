package com.yetnt.tokeniser.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.FormType;
import com.yetnt.tokeniser.types.values.AnyListValue;
import com.yetnt.tokeniser.types.values.StringListValue;
import com.yetnt.tokeniser.types.values.StringValue;
import com.yetnt.tokeniser.types.values.base.Any;

public class TestingForm extends Form {

    public StringValue title;
    public Object whatever;

    public TestingForm() {
        super("The Testing Form", FormType.PROCESS);
        add("Form Title", StringValue.class);
        add("Whatever", StringListValue.class);
    }

    @Override
    public void finish() throws BureaucraticError {
        super.finish();
        this.formType = FormType.GENERIC;
        title = get(StringValue.class, formEntries.get("Form Title"));
        whatever = get(StringListValue.class, formEntries.get("Whatever"));
    }

}
