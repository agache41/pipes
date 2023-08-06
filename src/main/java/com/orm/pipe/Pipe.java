package com.orm.pipe;

import com.orm.functional.ThrowingFunction;

public interface Pipe<Input, Output> {
    ThrowingFunction<Input, Output> function();
}
