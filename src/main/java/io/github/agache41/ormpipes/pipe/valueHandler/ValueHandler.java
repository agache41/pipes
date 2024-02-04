package io.github.agache41.ormpipes.pipe.valueHandler;

public interface ValueHandler {

    <Output> Output handleValue(Class<Output> outputClass,
                                Object inputValue);
}
