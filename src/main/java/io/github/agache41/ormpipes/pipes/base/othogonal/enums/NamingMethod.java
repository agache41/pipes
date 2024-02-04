package io.github.agache41.ormpipes.pipes.base.othogonal.enums;

import java.util.function.BiFunction;

public enum NamingMethod implements BiFunction<String, String, String> {
    AccessorNames((accessorName, fieldName) -> {
        if (accessorName.isEmpty()) throw new IllegalArgumentException(" No ***Accessor.name() found for " + fieldName + "!");
        return accessorName;
    }),
    JavaFieldNames((accessorName, fieldName) -> fieldName);

    private final BiFunction<String, String, String> method;

    NamingMethod(BiFunction<String, String, String> method) {
        this.method = method;
    }

    @Override
    public String apply(String accessorName, String fieldName) {
        return this.method.apply(accessorName, fieldName);
    }
}
