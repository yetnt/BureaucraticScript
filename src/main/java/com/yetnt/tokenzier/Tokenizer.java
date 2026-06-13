package com.yetnt.tokenzier;

import com.yetnt.errs.BureaucraticError;
import com.yetnt.lang.Chars;
import com.yetnt.tokenzier.types.Form;
import com.yetnt.tokenzier.types.ReferencialValue;
import com.yetnt.tokenzier.types.forms.DocumentHeader;
import com.yetnt.tokenzier.types.FormEntryValue;
import com.yetnt.tokenzier.types.values.ExpressionValue;
import com.yetnt.tokenzier.types.values.NumberValue;
import com.yetnt.tokenzier.types.values.StringValue;
import com.yetnt.tokenzier.utils.Comments;
import com.yetnt.tokenzier.utils.Find;
import com.yetnt.utils.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Tokenizer {
    public static void tokenize(Scanner scanner) throws BureaucraticError {

        ArrayList<Form> forms = new ArrayList<>();
        Form processingForm = Form.empty();
        ArrayList<String> ohterProcessedEntries = new ArrayList<>();
        boolean formClosed = true;

        while (scanner.hasNextLine()) {

            String line = Comments.decimate(scanner.nextLine()).trim();
            System.out.println(line);
            if (line.isBlank()) {
                // additional checks
                formClosed = true;
                continue;
            }

            if (processingForm.isEmptyForm() && formClosed) {
                formClosed = false;
                if (forms.isEmpty()) {
                    // We expect the Documents header form.
                    if (!line.startsWith("Documents Title"))
                        throw new BureaucraticError("No Documents Header");
                    processingForm = new DocumentHeader();
                    forms.add(processingForm);

                    var pair = createRaw(line);
                    processingForm.addEntry(ohterProcessedEntries, pair.first(), pair.second());
                    ohterProcessedEntries.add(pair.first());
                    continue;
                }

                // here there its some other form we need to start processing.
                if (!line.startsWith("Form Title"))
                    throw new BureaucraticError("Form Title was not provided");
                var pair = createRaw(line);
                processingForm = Form.toClassForm(pair.second());
            } else if (!processingForm.isEmptyForm() && !formClosed) {
                // finish the form.
                var pair = createRaw(line);
                processingForm.addEntry(
                        ohterProcessedEntries,
                        pair.first(),
                        pair.second()
                );
                ohterProcessedEntries.add(pair.first());
            }
        }
    }

    private static Pair<String, FormEntryValue<?>> createRaw(String line) throws BureaucraticError {
        if (!line.contains(Chars.SEPARATOR))
            throw new BureaucraticError("Invalid Form Entry");
        String[] parts = line.trim().split(Chars.SEPARATOR);

        if (parts.length != 2)
            throw new BureaucraticError("Invalid Form Entry");

        FormEntryValue<?> value = convertValue(parts[1].trim());

        return new Pair<>(parts[0], value);
    }

    public static FormEntryValue<?> convertValue(String input) {
        input = input.trim();
        try {
            return new NumberValue(input);
        } catch (NumberFormatException f) {
            Find.LeastImportantOperator lio = Find.leastImportantOperator(input);
            if (lio.index == -1) {
                // No operator found, check if wrapped in braces.
                Find.BracePairs pairs = Find.bracePairs(input);
                if (pairs.closedPairs().size() == 1) {
                    // remove outer braces
                    return convertValue(
                            input.substring(1, input.length() - 1)
                    );
                }
                // Not wrapped in a brace and not a number. Check for referencial
                FormEntryValue<?> referencial = ReferencialValue.getReferencial(input);

                return referencial == null ?
                        new StringValue(input)
                        : referencial;
            } else {
                // split the string into lhs and rhs
                String lhs = input.substring(0, lio.index);
                String rhs = input.substring(lio.index);
                FormEntryValue<?> left = convertValue(lhs);
                FormEntryValue<?> right = convertValue(rhs);
                return new ExpressionValue(input, left, right, lio.op);
            }
        }
    }
}
