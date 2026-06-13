package com.yetnt.tokenzier.types;

import com.yetnt.errs.BureaucraticError;

import java.util.ArrayList;
import java.util.HashMap;

public class Form {
    protected final String title;
    protected final FormType formType;
    protected final HashMap<String, FormEntry<?>> formEntries = new HashMap<>();
    protected final ArrayList<String> formEntryOrder = new ArrayList<>();

    protected Form(String title, FormType formType) {
        this.title = title;
        this.formType = formType;
    }

    public Form() {
        this("Empty Form", FormType.EMPTY);
    }

    public static Form empty() {
        return new Form("Empty Form", FormType.EMPTY);
    }

    public String getTitle() {
        return title;
    }

    public FormType getFormType() {
        return formType;
    }

    public HashMap<String, FormEntry<?>> getFormEntries() {
        return formEntries;
    }

    public boolean isValidEntry() {
        return true;
    }

    public boolean isEmptyForm() {
        return formType == FormType.EMPTY;
    }

    public void addEntry(ArrayList<String> ohterProcessedEntries, String key, FormEntryValue<?> value) throws BureaucraticError {
        // We're trying to process an entry.
        if (!formEntryOrder.get(ohterProcessedEntries.size()).equals(key.trim())) {
            // Wrong key in the order.
            throw new BureaucraticError(
                    "Form expected the key \"" + formEntryOrder.get(ohterProcessedEntries.size()) + "\" but got \"" + key + "\" instead."
            );
        }

        formEntries.put(key.trim(), new FormEntry<>(key.trim(), value));
    }

    public void finish() throws BureaucraticError {
        // Overrides implement.
    }

    public static Form toClassForm(String title) {
        // later when classes convert to specific class forms.
    }
}
