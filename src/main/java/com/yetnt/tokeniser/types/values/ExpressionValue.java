package com.yetnt.tokeniser.types.values;

import com.yetnt.tokeniser.types.values.base.BureaucraticType;
import com.yetnt.tokeniser.types.values.base.IComputableValue;
import com.yetnt.tokeniser.types.values.base.FormEntryValue;

/**
 * A token who represents an expression with a left and right. The lhs and rhs are defined to be
 * the generic {@link FormEntryValue} however they're expected to be of {@link IComputableValue}.
 * <p>
 *     Anything which contains an operator is treated as an expression.
 *     Example: <pre>{@code
 *     Form Title: Example
 *     Expression1: 1 + 2
 *     Expression2: (As Provided By Form 10) - 100 & 8
 *     Expression3: True || False
 *     }</pre>
 * </p>
 * @see IComputableValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "Expression")
public class ExpressionValue extends FormEntryValue<String> implements IComputableValue {

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
