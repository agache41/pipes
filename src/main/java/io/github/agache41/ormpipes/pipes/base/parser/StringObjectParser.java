package io.github.agache41.ormpipes.pipes.base.parser;

/**
 * <pre>
 * The type String object parser.
 * </pre>
 *
 * @param <T> the type parameter
 */
public abstract class StringObjectParser<T> extends Parser<T, String, T> {
    /**
     * <pre>
     * Instantiates a new String object parser.
     * </pre>
     *
     * @param clazz the clazz
     */
    public StringObjectParser(Class<T> clazz) {
        super(clazz, String.class);
    }

    /**
     * <pre>
     * Instantiates a new String object parser.
     * </pre>
     *
     * @param clazz the clazz
     * @param view  the view
     */
    public StringObjectParser(Class<T> clazz,
                              String view) {
        super(clazz, String.class, view);
    }
}
