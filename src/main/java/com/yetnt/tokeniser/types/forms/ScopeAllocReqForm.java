package com.yetnt.tokeniser.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.FormType;
import com.yetnt.tokeniser.types.values.NumberValue;
import com.yetnt.tokeniser.types.values.ReferToValue;
import com.yetnt.tokeniser.types.values.StringValue;

public class ScopeAllocReqForm extends Form {
    public ScopeAllocReqForm() {
        super("Scope Allocation Request", FormType.PROCESS);
        formEntryOrder.add("Form Title");
        formEntryOrder.add("Scoping Authority");
        formEntryOrder.add("Amount");
    }

    public StringValue formTitle;
    public ReferToValue scopingAuthority;
    public NumberValue amount;

    @Override
    public void finish() throws BureaucraticError {
        this.formType = FormType.GENERIC;
        formTitle = get(StringValue.class, formEntries.get("Form Title"));
        scopingAuthority = get(ReferToValue.class, formEntries.get("Scoping Authority"));
        amount = get(NumberValue.class, formEntries.get("Amount"));
    }

}
