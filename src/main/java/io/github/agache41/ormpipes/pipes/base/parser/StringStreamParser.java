package io.github.agache41.ormpipes.pipes.base.parser;

import java.util.stream.Stream;

public abstract class StringStreamParser<T> extends Parser<T, String, Stream<T>> {
    public StringStreamParser(Class<T> clazz) {
        super(clazz, String.class);
    }

    public StringStreamParser(Class<T> clazz, String view) {
        super(clazz, String.class, view);
    }
}
