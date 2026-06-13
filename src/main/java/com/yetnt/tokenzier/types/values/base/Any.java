package com.yetnt.tokenzier.types.values.base;

import com.yetnt.tokenzier.types.Form;
import com.yetnt.tokenzier.types.FormEntry;

/**
 * This is a marker interface used in {@link Form#getUntyped(Class, FormEntry)}
 * to indicate that any type of {@link FormEntryValue} is acceptable.
 * @author Lehlogonolo Poole
 */
@BureaucraticType(friendlyName = "[Any]")
public interface Any {
}
