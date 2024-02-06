package io.github.agache41.ormpipes.pipes.base.parser;

import java.util.stream.Stream;

/**
 * <pre>
 * The type Stream parser.
 * </pre>
 *
 * @param <T>     the type parameter
 * @param <Input> the type parameter
 */
public abstract class StreamParser<T, Input> extends Parser<T, Input, Stream<T>> {
    /**
     * <pre>
     * Instantiates a new Stream parser.
     * </pre>
     *
     * @param clazz      the clazz
     * @param inputClass the input class
     */
    public StreamParser(Class<T> clazz,
                        Class<Input> inputClass) {
        super(clazz, inputClass);
    }

    /**
     * <pre>
     * Instantiates a new Stream parser.
     * </pre>
     *
     * @param clazz      the clazz
     * @param inputClass the input class
     * @param view       the view
     */
    public StreamParser(Class<T> clazz,
                        Class<Input> inputClass,
                        String view) {
        super(clazz, inputClass, view);
    }
}
