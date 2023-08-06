package com.orm.pipes.base.format;

import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.reflection.annotator.Annotator;

import java.lang.annotation.Annotation;

public abstract class AbstractNullSafe<Input, Output> implements AnnotablePipe<Annotation, Input, Output> {

    protected ThrowingFunction<Input, Output> function;

    @Override
    public void configure(Annotation cfg) {
        final boolean nullSafe = Annotator.of(cfg)
                                          .getAccessor("nullSafe")
                                          .getAs(cfg, Boolean.class, Boolean.FALSE);
        this.function = this.function.nullSafe(nullSafe);
    }

    @Override
    public ThrowingFunction<Input, Output> function() {
        return this.function;
    }
}
