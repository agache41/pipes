package com.orm.pipes.spreadSheet.file;

import java.util.function.Function;

public enum Type {
    XSL(extension -> true), //
    XSLX(extension -> false), //
    AUTO(extension -> {
        if ("xls".equalsIgnoreCase(extension)) return true;
        else if ("xlsx".equalsIgnoreCase(extension)) return false;
        else throw new IllegalArgumentException(" Unknown file extension " + extension);
    });

    private final Function<String, Boolean> type;

    Type(Function<String, Boolean> type) {
        this.type = type;
    }

    public Boolean getType(String extension) {
        return this.type.apply(extension);
    }
}
