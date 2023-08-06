package com.orm.pipe.registry;


import com.orm.functional.StrongType;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.numeric.typeInteger.TypeInteger;
import com.orm.pipes.typeString.TypeString;
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
