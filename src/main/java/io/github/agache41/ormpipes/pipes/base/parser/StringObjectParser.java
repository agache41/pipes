package io.github.agache41.ormpipes.pipes.base.parser;

public abstract class StringObjectParser<T> extends Parser<T, String, T> {
    public StringObjectParser(Class<T> clazz) {
        super(clazz, String.class);
    }

    public StringObjectParser(Class<T> clazz, String view) {
        super(clazz, String.class, view);
    }
}
