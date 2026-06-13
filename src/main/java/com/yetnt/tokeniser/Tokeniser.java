package com.yetnt.tokeniser;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.lang.Chars;
import com.yetnt.tokeniser.types.Form;
import com.yetnt.tokeniser.types.FormType;
import com.yetnt.tokeniser.types.values.base.ILookUpValue;
import com.yetnt.tokeniser.types.forms.DocumentsHeader;
import com.yetnt.tokeniser.types.values.base.FormEntryValue;
import com.yetnt.tokeniser.types.values.*;
import com.yetnt.tokeniser.utils.Comments;
import com.yetnt.tokeniser.utils.Find;
import com.yetnt.utils.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tokeniser {

    public static void tokenise(Scanner scanner) throws BureaucraticError {
        ArrayList<Form> forms = new ArrayList<>();
        Form processingForm = Form.empty();
        ArrayList<String> ohterProcessedEntries = new ArrayList<>();
        boolean formClosed = true;
        int lineNumber = 0;

        while (scanner.hasNextLine()) {
            lineNumber++;
            String line = Comments.decimate(scanner.nextLine()).trim();
//            System.out.println(line);
            if (line.isBlank()) {
                if (!formClosed)
                    throw new BureaucraticError("Unexpected blank line, " + processingForm.getTitle() + " form not closed.", lineNumber);
                // additional checks
                formClosed = true;
                continue;
            }

            if (processingForm.isEmptyForm() && formClosed) {
                formClosed = false;
                if (forms.isEmpty()) {
                    // We expect the Documents header form.
                    if (!line.startsWith("Documents Title"))
                        throw new BureaucraticError("No Documents Header", lineNumber);
                    processingForm = new DocumentsHeader();
                    forms.add(processingForm);

                    var pair = createRaw(line, lineNumber);
                    processingForm.addEntry(ohterProcessedEntries, pair.first(), pair.second());
                    ohterProcessedEntries.add(pair.first());
                    continue;
                }

                // here there is some other form we need to start processing.
                if (!line.startsWith("Form Title"))
                    throw new BureaucraticError(
                            "Invalid Beginning of form. Form Title was not provided. Instead got \""
                            + line + "\""
                            ,lineNumber);
                var pair = createRaw(line, lineNumber);
                if (!(pair.second() instanceof StringValue stringValue))
                    throw new BureaucraticError("Form Title expects a string", lineNumber);
                processingForm = Form.toClassForm(stringValue.getValue(), lineNumber);
                processingForm.addEntry(
                        ohterProcessedEntries,
                        pair.first(),
                        pair.second()
                );
                forms.add(processingForm);
                ohterProcessedEntries.add(pair.first());

            } else if (!processingForm.isEmptyForm() && !formClosed) {
                // finish the form.
                var pair = createRaw(line, lineNumber);
                processingForm.addEntry(
                        // the form is responsible for ensuring the correct next form is placed.
                        ohterProcessedEntries,
                        pair.first(),
                        pair.second()
                );
                ohterProcessedEntries.add(pair.first());
                // The form may have finished and finalized itself.
                if (processingForm.getFormType() != FormType.PROCESS) {
                    // Completed form.
                    formClosed = true;
                    processingForm = new Form();
                    ohterProcessedEntries = new ArrayList<>();
                }
            }
        }

        System.out.println(forms.size());
    }

    private static Pair<String, FormEntryValue<?>> createRaw(String line, int lineNumber) throws BureaucraticError {
        if (!line.contains(Chars.SEPARATOR))
            throw new BureaucraticError("Invalid Form Entry", lineNumber);
        String[] parts = Find.splitByAmount(line.trim(), Chars.SEPARATOR, 2);

        if (parts.length != 2)
            throw new BureaucraticError("Invalid Form Entry", lineNumber);

        FormEntryValue<?> value = convertValue(parts[1].trim(), lineNumber).attachLineNumber(lineNumber);

        return new Pair<>(parts[0], value);
    }

    public static FormEntryValue<?> convertValue(String input, int lineNumber) throws BureaucraticError {
        input = input.trim();
        try {
            return new NumberValue(input);
        } catch (NumberFormatException f) {
            try {
                LocalTime time = LocalTime.parse(input, TimeValue.dateTimeFormatter);
                return new TimeValue(time);
            } catch (DateTimeParseException e) {
                try {
                    LocalDate date = LocalDate.parse(input, DateValue.dateTimeFormatter);
                    return new DateValue(date);
                } catch (DateTimeParseException ex) {
                    // It could be a boolean value.
                    return switch (input) {
                        case "True", "False" ->
                                new BooleanValue(Boolean.parseBoolean(input));
                        case "N/A", "Not Applicable" ->
                                new NotApplicableValue();
                        default -> convertValueExtracted(input, lineNumber);
                    };
                }
            }
        }
    }

    private static FormEntryValue<?> convertValueExtracted(String input, int lineNumber) throws BureaucraticError {
        Find.LeastImportantOperator lio = Find.leastImportantOperator(input);
        if (lio.index == -1) {
            // No operator found, check if wrapped in braces.
            Find.BracePairs pairs = Find.bracePairs(input);
            if (pairs.closedPairs().size() == 1) {
                // remove outer braces
                return convertValue(
                        input.substring(1, input.length() - 1), lineNumber
                );
            }
            // Not wrapped in a brace and not a number. Check for referencial
            FormEntryValue<?> referencial = ILookUpValue.getLookUpToken(input);

            if (referencial != null) {
                return referencial;
            }

            if (!input.contains(",")) {
                return new StringValue(input);
            }

            // Here it can only be a list of values.
            String[] parts = input.split(",");

            ArrayList<FormEntryValue<?>> values = new ArrayList<>();
            Boolean isStringValue = null;

            for (String part : parts) {
                // check if its a referencial.
                part = part.trim();
                FormEntryValue<?> ref = convertValue(part, lineNumber); // we could shortcut and just check referencial directly, but braces.
                if (!(ref instanceof ReferToValue || ref instanceof StringValue))
                    throw new BureaucraticError(
                            "List values can only be a reference or a string value. Value not supported: \"" + input + "\"",
                            lineNumber
                    );
                if (isStringValue == null) {
                    isStringValue = ref instanceof StringValue;
                } else {
                    if (isStringValue != (ref instanceof StringValue))
                        throw new BureaucraticError(
                                "The given list can only be of a single type. The first value was a " +
                                        (isStringValue ? "string" : "reference") +
                                        " but the nth value was a " +
                                        (isStringValue ? "string" : "reference"),
                                lineNumber
                        );
                }
                values.add(ref);
            }

            return values.getFirst() instanceof StringValue ?
                    new StringListValue(values) :
                    new ReferenceListValue(values);
        } else {
            // split the string into lhs and rhs
            String lhs = input.substring(0, lio.index).trim();
            String rhs = input.substring(lio.index
                    + (lio.op.length() == 1 ? 1 : 2)).trim();
            FormEntryValue<?> left = convertValue(lhs, lineNumber);
            FormEntryValue<?> right = convertValue(rhs, lineNumber);
            return new ExpressionValue(input, left, right, lio.op);
        }
    }
}
