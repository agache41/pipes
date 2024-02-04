package io.github.agache41.ormpipes.pipes.base.format;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;

import java.lang.annotation.Annotation;

public abstract class AbstractFormat<A extends Annotation, Input> extends AbstractFormatter<A, Input, String> implements AnnotablePipe<A, Input, String> {

    protected ThrowingFunction<Input, String> function;

    @Override
    public StrongType getOutputType() {
        return TypeString.strongType;
    }

    @Override
    public void configure(A cfg) {
        super.configure(cfg);
        if (this.simple) {
            this.function = Object::toString;
        }
    }

    @Override
    public ThrowingFunction<Input, String> function() {
        return this.function.nullSafe(this.nullSafe)
                            .blankOutputSafe(this.blankSafe);


    }
}
