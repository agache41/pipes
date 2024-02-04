package io.github.agache41.ormpipes.pipe.registry;


import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.numeric.typeInteger.TypeInteger;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Objects;

@Execution(ExecutionMode.CONCURRENT)
public class ToStringPipeTest implements AnnotablePipe<ToStringAnnotTest, Integer, String> {
    @Override
    public void configure(ToStringAnnotTest cfg) {

    }

    @Override
    public ThrowingFunction<Integer, String> function() {
        return Objects::toString;
    }

    @Override
    public StrongType getInputType() {
        return TypeInteger.strongType;
    }

    @Override
    public StrongType getOutputType() {
        return TypeString.strongType;
    }
}
