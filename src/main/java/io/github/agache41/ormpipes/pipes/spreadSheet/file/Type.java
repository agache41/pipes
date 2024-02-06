package io.github.agache41.ormpipes.pipes.spreadSheet.file;

import java.util.function.Function;

/**
 * <pre>
 * The enum Type.
 * </pre>
 */
public enum Type {
    /**
     * <pre>
     * Xsl type.
     * </pre>
     */
    XSL(extension -> true), //
    /**
     * <pre>
     * Xslx type.
     * </pre>
     */
    XSLX(extension -> false), //
    /**
     * <pre>
     * The Auto.
     * </pre>
     */
    AUTO(extension -> {
        if ("xls".equalsIgnoreCase(extension)) {
            return true;
        } else if ("xlsx".equalsIgnoreCase(extension)) {
            return false;
        } else {
            throw new IllegalArgumentException(" Unknown file extension " + extension);
        }
    });

    private final Function<String, Boolean> type;

    Type(Function<String, Boolean> type) {
        this.type = type;
    }

    /**
     * <pre>
     * Gets type.
     * </pre>
     *
     * @param extension the extension
     * @return the type
     */
    public Boolean getType(String extension) {
        return this.type.apply(extension);
    }
}
