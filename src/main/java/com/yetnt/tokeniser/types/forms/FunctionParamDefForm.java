package com.yetnt.tokeniser.types.forms;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.FormEntry;
import com.yetnt.tokeniser.types.FormType;
import com.yetnt.tokeniser.types.enums.TypesInputEnum;
import com.yetnt.tokeniser.types.values.BooleanValue;
import com.yetnt.tokeniser.types.values.EnumValue;
import com.yetnt.tokeniser.types.values.StringValue;
import com.yetnt.tokeniser.types.values.base.Any;

public class FunctionParamDefForm extends Form {
    public FunctionParamDefForm() {
        super("Function Parameter Definition", FormType.PROCESS);
        add("Form Title", StringValue.class);
        add("Parameter Name",  StringValue.class);
        add("Expected Type", EnumValue.class, TypesInputEnum.class);
        add("Optional",  BooleanValue.class);
        add("Default Value", Any.class);
    }

    public StringValue parameterName;
    public EnumValue<TypesInputEnum> expectedType;
    public FormEntry<EnumValue<TypesInputEnum>> expectedTypeFormEntry =
            new FormEntry<>("Expected Type", new EnumValue<>("Any", TypesInputEnum.ANY));
    public BooleanValue optional;
    public Object defaultValue;

    @Override
    public void finish() throws BureaucraticError {
        super.finish();
        this.formType = FormType.GENERIC;
        parameterName = get(StringValue.class, formEntries.get("Parameter Name"));
        expectedType = get(expectedTypeFormEntry, formEntries.get("Expected Type"));
        optional = get(BooleanValue.class, formEntries.get("Optional"));
        defaultValue = getUntyped(Any.class, formEntries.get("Default Value"));
    }

}
