package io.github.agache41.ormpipes.pipes.base.format;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;

import java.lang.annotation.Annotation;

public abstract class AbstractParse<A extends Annotation, Output> extends AbstractFormatter<A, String, Output> implements AnnotablePipe<A, String, Output> {

    protected ThrowingFunction<String, Output> function;

    @Override
    public StrongType getInputType() {
        return TypeString.strongType;
    }

    @Override
    public void configure(A cfg) {
        super.configure(cfg);
    }

    @Override
    public ThrowingFunction<String, Output> function() {
        return this.function.nullSafe(this.nullSafe)
                            .blankInputSafe(this.blankSafe)
                            .noException(this.noException);
    }
}
