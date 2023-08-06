package com.orm.pipes.base.parser;

import java.util.stream.Stream;

public abstract class StreamParser<T, Input> extends Parser<T, Input, Stream<T>> {
    public StreamParser(Class<T> clazz, Class<Input> inputClass) {
        super(clazz, inputClass);
    }

    public StreamParser(Class<T> clazz, Class<Input> inputClass, String view) {
        super(clazz, inputClass, view);
    }
}
