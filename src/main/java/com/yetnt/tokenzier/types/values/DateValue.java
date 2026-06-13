package com.yetnt.tokenzier.types.values;

import com.yetnt.tokenzier.types.values.base.IAtomicValue;
import com.yetnt.tokenzier.types.values.base.BureaucraticType;
import com.yetnt.tokenzier.types.values.base.FormEntryValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A token who is just a wrapper around Java's {@link LocalDate} type.
 * <p>
 *     This token is used to represent a date. The format is strictly {@code dd/MM/yyyy}.
 * </p>
 * <p>
 *     Examples:
 *     <pre>{@code
 *     Form Title: Example
 *     Date: 01/01/2023
 *     }</pre>
 * </p>
 * @see IAtomicValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "Date")
public class DateValue extends FormEntryValue<LocalDate> implements IAtomicValue {

    public static DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public DateValue(LocalDate value) {
        super(value);
    }

}
