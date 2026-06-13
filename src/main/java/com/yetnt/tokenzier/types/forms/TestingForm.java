package com.yetnt.tokenzier.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.types.Form;
import com.yetnt.tokenzier.types.FormType;
import com.yetnt.tokenzier.types.values.StringValue;
import com.yetnt.tokenzier.types.values.base.Any;
import com.yetnt.tokenzier.types.values.base.IAtomicValue;
import com.yetnt.tokenzier.types.values.base.IComputableValue;
import com.yetnt.tokenzier.types.values.base.ILookUpValue;

public class TestingForm extends Form {

    public StringValue title;
    public Object whatever;

    public TestingForm() {
        super("The Testing Form", FormType.PROCESS);
        formEntryOrder.add("Form Title");
        formEntryOrder.add("Whatever");
    }

    @Override
    public void finish() throws BureaucraticError {
        super.finish();
        this.formType = FormType.GENERIC;
        title = get(StringValue.class, formEntries.get("Form Title"));
        whatever = getUntyped(Any.class, formEntries.get("Whatever"));
    }

}
