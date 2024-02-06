package io.github.agache41.ormpipes.pipe.valueHandler;

/**
 * <pre>
 * The interface Value handler.
 * </pre>
 */
public interface ValueHandler {

    /**
     * <pre>
     * Handle a value output for a specific type and input value
     * </pre>
     *
     * @param <Output>    the type parameter
     * @param outputClass the output class
     * @param inputValue  the input value
     * @return the output
     */
    <Output> Output handleValue(Class<Output> outputClass,
                                Object inputValue);
}
