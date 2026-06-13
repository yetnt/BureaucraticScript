package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.FormEntryValue;

public class ExpressionValue extends FormEntryValue<String> {

    private final FormEntryValue<?> lhs;
    private final FormEntryValue<?> rhs;
    private final String operator;

    public ExpressionValue(String fullExpression, FormEntryValue<?> lhs, FormEntryValue<?> rhs, String operator) {
        super(fullExpression);
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    public FormEntryValue<?> getLhs() {
        return lhs;
    }

    public FormEntryValue<?> getRhs() {
        return rhs;
    }

    public String getOperator() {
        return operator;
    }

}
