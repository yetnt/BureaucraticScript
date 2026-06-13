package com.yetnt.tokeniser.types;

/**
 * Represents the type of a {@link Form}.
 * @see Form
 * @author Lehlogonolo Poole
 */
public enum FormType {
    /**
     * Any complete form
     */
    GENERIC,
    /**
     * A documents header form
     */
    DOCUMENTS_HEADER,
    /**
     * A license form
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
