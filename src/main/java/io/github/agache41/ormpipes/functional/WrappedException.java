package io.github.agache41.ormpipes.functional;

/**
 * <pre>
 * The type Wrapped exception.
 * </pre>
 */
public class WrappedException extends RuntimeException {
    private final Throwable cause;

    /**
     * <pre>
     * Instantiates a new Wrapped exception.
     * </pre>
     *
     * @param cause the cause
     */
    public WrappedException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Throwable getCause() {
        return cause;
    }
}
