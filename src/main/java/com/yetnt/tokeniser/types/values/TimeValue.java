package com.yetnt.tokeniser.types.values;

import com.yetnt.tokeniser.types.values.base.IAtomicValue;
import com.yetnt.tokeniser.types.values.base.BureaucraticType;
import com.yetnt.tokeniser.types.values.base.FormEntryValue;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A token who is just a wrapper around Java's {@link LocalTime} type.
 * <p>
 *     This token is used to represent a time.
 * </p>
 * <p>
 *     Examples:
 *     <pre>{@code
 *     Form Title: Example
 *     Time: 14:30
 *     }</pre>
 * </p>
 * @see IAtomicValue
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "Time")
public class TimeValue extends FormEntryValue<LocalTime> implements IAtomicValue {

    public static DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm");


    public TimeValue(LocalTime value) {
        super(value);
    }
}
