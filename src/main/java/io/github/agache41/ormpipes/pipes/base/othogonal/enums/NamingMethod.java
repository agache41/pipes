package io.github.agache41.ormpipes.pipes.base.othogonal.enums;

import java.util.function.BiFunction;

/**
 * <pre>
 * The enum Naming method.
 * </pre>
 */
public enum NamingMethod implements BiFunction<String, String, String> {
    /**
     * <pre>
     * The Accessor names.
     * </pre>
     */
    AccessorNames((accessorName, fieldName) -> {
        if (accessorName.isEmpty()) {
            throw new IllegalArgumentException(" No ***Accessor.name() found for " + fieldName + "!");
        }
        return accessorName;
    }),
    /**
     * <pre>
     * Java field names naming method.
     * </pre>
     */
    JavaFieldNames((accessorName, fieldName) -> fieldName);

    private final BiFunction<String, String, String> method;

    NamingMethod(BiFunction<String, String, String> method) {
        this.method = method;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String apply(String accessorName,
                        String fieldName) {
        return this.method.apply(accessorName, fieldName);
    }
}
