package com.yetnt.tokeniser.types;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokeniser.types.forms.*;
import com.yetnt.tokeniser.types.values.*;
import com.yetnt.tokeniser.types.values.base.*;
import com.yetnt.utils.SamePair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The most, important class.
 * <p>
 *     A Form, is defined within a {@code .bdocs} file as a set of {@link FormEntry} all separated by a
 *     new line. Multiples forms are separated by multiple new lines
 * </p>
 * <p>
 *     All Forms are required to have a {@code Form Title: String} {@link FormEntry} as it's first entry
 *     except the {@link DocumentsHeader}.
 * </p>
 * @see FormEntry
 * @see FormEntryValue
 * @see FormType
 * @author Lehlogonolo Poole
 */
public class Form {
    protected final String title;
    protected FormType formType;
    protected final HashMap<String, FormEntry<?>> formEntries = new HashMap<>();
    protected final ArrayList<String> formEntryOrder = new ArrayList<>();

    /**
     * Constructs a Form with the specified title and form type.
     * @param title The title of the form
     * @param formType The form type.
     */
    protected Form(String title, FormType formType) {
        this.title = title;
        this.formType = formType;
    }

    /**
     * Constructs an empty Form with a default title "Empty Form" and {@link FormType#EMPTY}.
     */
    public Form() {
        this("Empty Form", FormType.EMPTY);
    }

    /**
     * Creates and returns an empty Form instance.
     *
     * @return An empty Form.
     */
    public static Form empty() {
        return new Form("Empty Form", FormType.EMPTY);
    }

    /**
     * Returns the title of this form.
     *
     * @return The title of the form.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the type of this form.
     *
     * @return The {@link FormType} of the form.
     */
    public FormType getFormType() {
        return formType;
    }

    /**
     * Returns a HashMap containing all the form entries, where the key is the entry's key
     * and the value is the {@link FormEntry} object.
     *
     * @return A HashMap of form entries.
     */
    public HashMap<String, FormEntry<?>> getFormEntries() {
        return formEntries;
    }

    /**
     * Checks if this form is an empty form.
     *
     * @return {@code true} if the form type is {@link FormType#EMPTY}, {@code false} otherwise.
     */
    public boolean isEmptyForm() {
        return formType == FormType.EMPTY;
    }

    /**
     * Adds a new entry to the form, performing validation based on the expected order and type.
     * If all expected entries have been added, the {@link #finish()} method is called.
     * @throws BureaucraticError if the number of entries exceeds expectations or if the key does not match the expected order.
     */
    public void addEntry(ArrayList<String> ohterProcessedEntries, String key, FormEntryValue<?> value) throws BureaucraticError {
        if (this.formType != FormType.PROCESS) this.formType = FormType.PROCESS;
        if (ohterProcessedEntries.size() >= formEntryOrder.size()) {
            // In this case, since we're adding one more to the entry, if there are more entries
            // than the form provides or its already equal, then it's too much.
            throw new BureaucraticError("The form expects only (" + formEntryOrder.size() + ") entries. but was given (" + ohterProcessedEntries.size() + ")",
                    value.getLineNumber());
        }
        // We're trying to process an entry.
        if (!formEntryOrder.get(ohterProcessedEntries.size()).equals(key.trim())) {
            // Wrong key in the order.
            throw new BureaucraticError(
                    "Form expected the key \"" + formEntryOrder.get(ohterProcessedEntries.size()) + "\" but got \"" + key + "\" instead.",
                    value.getLineNumber()
            );
        }

        formEntries.put(key.trim(), new FormEntry<>(key.trim(), value));

        if (ohterProcessedEntries.size() == formEntryOrder.size() - 1)
            // In this case, we've added the final thing we need. Parse it.
            finish();

    }

    /**
     * Called when all expected entries have been added to the form.
     * @implSpec Subclasses can override this method to perform final processing or validation.
     * @throws BureaucraticError if any errors occur during the finalisation process.
     */
    public void finish() throws BureaucraticError {
        // Overrides implement.
    }

    /**
     * Converts a given title into its corresponding {@link Form} class instance.
     * @param title The title of the form to convert.
     * @param lineNumber The line number where the form title was found, used for error reporting.
     * @return An instance of the {@link Form} subclass corresponding to the given title.
     * @throws BureaucraticError if no form with the specified title is found.
     */
    public static Form toClassForm(String title, int lineNumber) throws BureaucraticError {
        ArrayList<Form> allForms = new ArrayList<>() ;
        allForms.add(new DocumentsHeader());
        allForms.add(new LicenseForm());
        allForms.add(new TestingForm());

        for (Form form : allForms) {
            if (form.getTitle().equals(title)) {
                return form;
            }
        }

        throw new BureaucraticError(
                "No Form with the title \"" + title + "\" was found.",
                lineNumber
        );
    }

    /**
     * Retrieves the friendly names of two {@link FormEntryValue} classes, as defined by their
     * {@link BureaucraticType} annotation.
     * @param clazzU The first {@link FormEntryValue} class.
     * @param clazzX The second {@link FormEntryValue} class.
     * @return A {@link SamePair} containing the friendly names of the two classes.
     */
    private <U extends FormEntryValue<?>, V extends FormEntryValue<?>>
    SamePair<String> getClassFriendlyNames(Class<U> clazzU, Class<V> clazzX) {
        return new SamePair<>(
                clazzU.getAnnotation(BureaucraticType.class).friendlyName(),
                clazzX.getAnnotation(BureaucraticType.class).friendlyName()
        );
    }

    /**
     * Retrieves the value of a {@link FormEntry} and casts it to the expected type.
     * Performs type checking and throws a {@link BureaucraticError} if the actual type does not match the expected type.
     * @param expected The expected class type of the {@link FormEntryValue}.
     * @param entry The {@link FormEntry} to retrieve the value from.
     * @param <T> The generic type of the expected {@link FormEntryValue}.
     * @return The value of the {@link FormEntry}, cast to the expected type.
     * @throws BureaucraticError if the actual type of the entry's value does not match the expected type.
     */
    protected <T extends FormEntryValue<?>> T get(Class<T> expected, FormEntry<?> entry) throws BureaucraticError {
        SamePair<String> friendlyNames = getClassFriendlyNames(
                expected, entry.getValue().getClass()
        );
        if (expected != entry.getValue().getClass()) {
            throw new BureaucraticError(
                    entry.getKey() + " expects a " + friendlyNames.first() + " but instead got a "
                    + friendlyNames.second(),
                    entry.getValue().getLineNumber()
            );
        }

        return (T) entry.getValue();
    }

    /**
     * Retrieves an {@link EnumValue} from a {@link FormEntry}.
     * This method expects the entry's value to be a {@link StringValue} and attempts to convert it
     * into the specified {@link EnumValue}.
     * @param enumValueInstance An instance of {@link FormEntry} containing the {@link EnumValue} to populate.
     * @param entry The {@link FormEntry} from which to extract the string value.
     * @param <R> The type of the enum.
     * @return The populated {@link EnumValue}.
     * @throws BureaucraticError if the entry's value is not a {@link StringValue} or if the conversion fails.
     */
    protected <R extends EnumValues> EnumValue<R> get(
            FormEntry<EnumValue<R>> enumValueInstance,
            FormEntry<?> entry) throws BureaucraticError {
        Class<StringValue> expected = StringValue.class;
        SamePair<String> friendlyNames = getClassFriendlyNames(
                expected, entry.getValue().getClass()
        );
        if (expected != entry.getValue().getClass()) {
            throw new BureaucraticError(
                    entry.getKey() + " expects a " + friendlyNames.first() + " but instead got a "
                            + friendlyNames.second(),
                    entry.getValue().getLineNumber()
            );
        }

        enumValueInstance.getValue().fromStringValue((FormEntry<StringValue>) entry);
        return enumValueInstance.getValue();
    }

    /**
     * Retrieves an {@link EnumListValue} from a {@link FormEntry}.
     * This method expects the entry's value to be a {@link StringListValue} and attempts to convert it
     * into the specified {@link EnumListValue}. The boolean parameter {@code t} is unused and can be removed.
     * @param enumListValueInstance An instance of {@link FormEntry} containing the {@link EnumListValue} to populate.
     * @param entry The {@link FormEntry} from which to extract the string list value.
     * @param t An unused boolean parameter. (Avoid type erasure with {@link #get(Class, FormEntry)}
     * @param <R> The type of the enum.
     * @return The populated {@link EnumListValue}.
     * @throws BureaucraticError if the entry's value is not a {@link StringListValue} or if the conversion fails.
     */
    protected <R extends EnumValues> EnumListValue<R> get(
            FormEntry<EnumListValue<R>> enumListValueInstance,
            FormEntry<?> entry,
            boolean t) throws BureaucraticError {
        Class<StringListValue> expected = StringListValue.class;
        SamePair<String> friendlyNames = getClassFriendlyNames(
                expected, entry.getValue().getClass()
        );
        if (expected != entry.getValue().getClass()) {
            throw new BureaucraticError(
                    entry.getKey() + " expects a " + friendlyNames.first() + " but instead got a "
                            + friendlyNames.second(),
                    entry.getValue().getLineNumber()
            );
        }

        enumListValueInstance.getValue().fromListStringValues(entry);
        return enumListValueInstance.getValue();
    }

    /**
     * Retrieves the value of a {@link FormEntry} and casts it to the expected type, without strict type checking.
     * This method is useful when the exact type of the value is not known at compile time, but it is known
     * to implement a certain interface or be a subclass of a certain class.
     * @param expected The expected class or interface type.
     * @param entry The {@link FormEntry} to retrieve the value from.
     * @return The value of the {@link FormEntry}, cast to {@link Object}.
     * @throws BureaucraticError if the actual type of the entry's value does not match the expected type.
     */
    public Object getUntyped(Class<?> expected, FormEntry<?> entry) throws BureaucraticError {
        String expectedFriendlyName = expected.getAnnotation(BureaucraticType.class).friendlyName();
        String actualFriendlyName = entry.getValue().getClass().getAnnotation(BureaucraticType.class).friendlyName();

        if (expected == Any.class) {
            return entry.getValue();
        }

        if (!expected.isInstance(entry.getValue())) {
            throw new BureaucraticError(
                    entry.getKey() + " expects a " + expectedFriendlyName + " but instead got a "
                            + actualFriendlyName,
                    entry.getValue().getLineNumber()
            );
        }

        return entry.getValue();
    }
}
