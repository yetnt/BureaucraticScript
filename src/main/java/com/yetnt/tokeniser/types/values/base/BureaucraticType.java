package com.yetnt.tokeniser.types.values.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark classes as Bureaucratic types.
 * It provides a friendly name for the type, which can be used in user interfaces or error messages.
 * @see FormEntryValue
 * @author Lehlogonolo Poole
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BureaucraticType {
    String friendlyName();
}