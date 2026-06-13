package com.yetnt.tokenzier.types.values.base;

import com.yetnt.tokenzier.types.values.*;

/**
 * One of, if not the most important classes of this project. The base class
 * for all values that can be assigned to a FormEntry.
 *
 * <p>
 *     Generally, all subclasses are just wrappers for java types such as
 *    {@link StringValue}, {@link DateValue}, {@link TimeValue} and {@link BooleanValue}.
 *    ({@link NumberValue} is also one but wraps both a double and integer as a single type).
 *    However, other classes refer to specific language features unique to this project
 *    and hence have other properties, methods and/or fields which the base class wouldn't have.
 * </p>
 * <p>
 *     There are, also marker interfaces to bucket multiple concrete types under a similar use case.
 *     Such as {@link IComputableValue} or {@link IListValue}. These interfaces are denoted by the
 *     "I" prefix.
 * </p>
 * @param <T> The type of the value.
 * @see IComputableValue
 * @see IAtomicValue
 * @see IListValue
 * @see ILookUpValue
 * @author Lehlogonolo Poole
 */
public class FormEntryValue<T> {
    protected T value;
    protected int lineNumber;

    public FormEntryValue(T value) {
        this.value = value;
    }

    public FormEntryValue() {}

    public T getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public FormEntryValue<?> attachLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }
}
