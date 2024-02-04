package io.github.agache41.ormpipes.pipe;

import io.github.agache41.ormpipes.functional.ThrowingFunction;

public interface Pipe<Input, Output> {
    ThrowingFunction<Input, Output> function();
}
