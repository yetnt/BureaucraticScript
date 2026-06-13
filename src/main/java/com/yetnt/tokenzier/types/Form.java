package com.yetnt.tokenzier.types;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.tokenzier.types.forms.DocumentsHeader;
import com.yetnt.tokenzier.types.forms.LicenseForm;

import java.util.ArrayList;
import java.util.HashMap;

public class Form {
    protected final String title;
    protected FormType formType;
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
        if (this.formType != FormType.PROCESS) this.formType = FormType.PROCESS;
        if (ohterProcessedEntries.size() >= formEntryOrder.size()) {
            // In this case, since we're adding one more to the entry, if there are more entries
            // than the form provides or its already equal, then it's too much.
            throw new BureaucraticError("The form expects only (" + formEntryOrder.size() + ") entries. but was given (" + ohterProcessedEntries.size() + ")");
        }
        // We're trying to process an entry.
        if (!formEntryOrder.get(ohterProcessedEntries.size()).equals(key.trim())) {
            // Wrong key in the order.
            throw new BureaucraticError(
                    "Form expected the key \"" + formEntryOrder.get(ohterProcessedEntries.size()) + "\" but got \"" + key + "\" instead."
            );
        }

        formEntries.put(key.trim(), new FormEntry<>(key.trim(), value));

        if (ohterProcessedEntries.size() == formEntryOrder.size() - 1)
            // In this case, we've added the final thing we need. Parse it.
            finish();
        
    }

    public void finish() throws BureaucraticError {
        // Overrides implement.
    }

    public static Form toClassForm(String title) throws BureaucraticError {
        ArrayList<Form> allForms = new ArrayList<>() ;
        allForms.add(new DocumentsHeader());
        allForms.add(new LicenseForm());

        for (Form form : allForms) {
            if (form.getTitle().equals(title)) {
                return form;
            }
        }

        throw new BureaucraticError(
                "No Form with the title \"" + title + "\" was found."
        );

    }
}
