package io.github.agache41.ormpipes.pipes.base.parser;

import java.util.stream.Stream;

/**
 * <pre>
 * The type String stream parser.
 * </pre>
 *
 * @param <T> the type parameter
 */
public abstract class StringStreamParser<T> extends Parser<T, String, Stream<T>> {
    /**
     * <pre>
     * Instantiates a new String stream parser.
     * </pre>
     *
     * @param clazz the clazz
     */
    public StringStreamParser(Class<T> clazz) {
        super(clazz, String.class);
    }

    /**
     * <pre>
     * Instantiates a new String stream parser.
     * </pre>
     *
     * @param clazz the clazz
     * @param view  the view
     */
    public StringStreamParser(Class<T> clazz,
                              String view) {
        super(clazz, String.class, view);
    }
}
