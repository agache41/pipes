package io.github.agache41.ormpipes.pipe.exception;

/**
 * <pre>
 * The type Pipe exception.
 * </pre>
 */
public class PipeException extends RuntimeException {
    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     */
    public PipeException() {
    }

    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     *
     * @param message the message
     */
    public PipeException(String message) {
        super(message);
    }

    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     *
     * @param message the message
     * @param cause   the cause
     */
    public PipeException(String message,
                         Throwable cause) {
        super(message, cause);
    }

    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     *
     * @param cause the cause
     */
    public PipeException(Throwable cause) {
        super(cause);
    }

    /**
     * <pre>
     * Instantiates a new Pipe exception.
     * </pre>
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public PipeException(String message,
                         Throwable cause,
                         boolean enableSuppression,
                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
