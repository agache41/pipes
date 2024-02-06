package io.github.agache41.ormpipes.pipes.accessor;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * <pre>
 * The type Getter.
 * </pre>
 */
public class Getter implements AnnotablePipe<Accessor, Object, Object> {
    private io.github.agache41.annotator.accessor.Accessor<?> onAccessor;

    private Class<?> onClass;

    private Type type;

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return StrongType.of(this.type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getInputType() {
        return StrongType.of(this.onClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(Accessor cfg) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(Accessor cfg,
                          Class<?> onClass,
                          java.lang.reflect.Field onField,
                          Method onMethod,
                          io.github.agache41.annotator.accessor.Accessor<?> onAccessor,
                          String operation) {
        this.onAccessor = onAccessor;
        this.onClass = onClass;
        this.type = onAccessor.getGenericType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Object, Object> function() {
        return this.onAccessor::get;
    }
}
