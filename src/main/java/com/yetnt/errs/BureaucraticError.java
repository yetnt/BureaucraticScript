package com.yetnt.errs;

import com.yetnt.utils.CCol;

public class BureaucraticError extends Exception {

    public BureaucraticError(String message, int lineNumber) {
        super(
                CCol.print(message, CCol.TEXT.RED, CCol.FONT.ITALIC, CCol.TEXT.RED) + " " +
                        CCol.print("[On line " + lineNumber + "]", CCol.TEXT.YELLOW, CCol.FONT.BOLD)
        );
    }

    public BureaucraticError(String message, Throwable cause) {
        super(message, cause);
    }

    public BureaucraticError(Throwable cause) {
        super(cause);
    }
}
