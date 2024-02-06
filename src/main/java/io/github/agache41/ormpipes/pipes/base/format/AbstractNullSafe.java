package io.github.agache41.ormpipes.pipes.base.format;

import io.github.agache41.annotator.annotator.Annotator;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.annotation.Annotation;

/**
 * <pre>
 * The type Abstract null safe.
 * </pre>
 *
 * @param <Input>  the type parameter
 * @param <Output> the type parameter
 */
public abstract class AbstractNullSafe<Input, Output> implements AnnotablePipe<Annotation, Input, Output> {

    /**
     * <pre>
     * The Function.
     * </pre>
     */
    protected ThrowingFunction<Input, Output> function;

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(Annotation cfg) {
        final boolean nullSafe = Annotator.of(cfg)
                                          .getAccessor("nullSafe")
                                          .getAs(cfg, Boolean.class, Boolean.FALSE);
        this.function = this.function.nullSafe(nullSafe);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Input, Output> function() {
        return this.function;
    }
}
