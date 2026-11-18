package com.yetnt.errs;

import io.github.yetnt.utils.builders.AnsiColour;

public class BureaucraticError extends Exception {

    public BureaucraticError(String message, int lineNumber) {
        super(
                AnsiColour.print(message, AnsiColour.FORE.RED, AnsiColour.FONT.ITALIC, AnsiColour.FORE.RED) + " " +
                        AnsiColour.print("[On line " + lineNumber + "]", AnsiColour.FORE.YELLOW, AnsiColour.FONT.BOLD)
        );
    }

    public BureaucraticError(String message, Throwable cause) {
        super(message, cause);
    }

    public BureaucraticError(Throwable cause) {
        super(cause);
    }
}
