package com.yetnt.tokenzier.types;

public enum FormType {
    /**
     * Generic Complete Form
     */
    GENERIC,
    /**
     * The .bdocs header form
     */
    DOCUMENTS_HEADER,
    /**
     * The license form
     */
    LICENSE,
    /**
     * An empty form
     */
    EMPTY,
    /**
     * A form that's being processed.
     */
    PROCESS
}
