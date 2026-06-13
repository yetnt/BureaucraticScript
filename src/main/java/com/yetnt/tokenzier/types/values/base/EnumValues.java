package com.yetnt.tokenzier.types.values.base;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.types.Form;
import com.yetnt.tokenzier.types.FormEntry;
import com.yetnt.tokenzier.types.forms.LicenseForm;
import com.yetnt.tokenzier.types.values.*;

import java.util.ArrayList;

/**
 *  Interface that defines the set of valid values that can be used by {@link EnumValue} and
 *  {@link EnumListValue}.
 *  <p>
 *      This is implemented by enums that define a constrained domain of accepted string inputs
 *      which can be parsed and validated by a {@link FormEntry}
 *  </p>
 *  @implNote <p>
 *      The {@link Form} is usually responsible for creating the enum subclass and it's validation
 *      logic. For encapsulation and better management.
 *      An example would be the {@link LicenseForm.LicensePropertiesEnum} which is a subclass of
 *      {@link LicenseForm}
 *  </p>
 * @see EnumValue
 * @see EnumListValue
 * @see LicenseForm.LicensePropertiesEnum
 * @author Lehlogonolo Poole
 */
public interface EnumValues {
    /**
     * Returns the string representation of the enum value.
     * @return The string representation of the enum value.
     */
    String getValue();

    /**
     * Whether the given input by the user is a valid input for this enum.
     * @param input The input by the user.
     * @return Whether the input is valid.
     */
    default boolean isValid(String input) {
        return getAcceptedValues().contains(input);
    };

    /**
     * An ArrayList of accepted input strings. usually just the enum values as a string
     * @return An ArrayList of accepted input strings.
     */
    ArrayList<String> getAcceptedValues();

    /**
     * Returns the given enum value for the string at the specified line number. If the input is not
     * valid it errors.
     * @param otherValue The value to parse.
     * @param lineNumber The line number of the value.
     * @return The enum value for the string.
     * @param <T> The type of the enum.
     * @throws BureaucraticError If the input is not valid.
     */
    <T extends EnumValues> T getEnumValueFrom(String otherValue, int lineNumber) throws BureaucraticError;
}
