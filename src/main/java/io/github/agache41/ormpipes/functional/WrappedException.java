package io.github.agache41.ormpipes.functional;

public class WrappedException extends RuntimeException {
    private final Throwable cause;

    public WrappedException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
