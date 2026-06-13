package com.yetnt.tokenzier.types.values.base;

import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.values.BooleanValue;
import com.yetnt.tokenzier.types.values.ExpressionValue;
import com.yetnt.tokenzier.types.values.NumberValue;

/**
 * Any value of a {@link FormEntry} that resolves to a number or boolean.
 * This is used to categorize {@link NumberValue}, {@link BooleanValue}
 * and {@link ExpressionValue} as similar as booleans and numbers can participate in expressions.
 * @see NumberValue
 * @see BooleanValue
 * @see ExpressionValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "[value resolving to number or boolean]")
public interface IComputableValue extends IAtomicValue {
}
