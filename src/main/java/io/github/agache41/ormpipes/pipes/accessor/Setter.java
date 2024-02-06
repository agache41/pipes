package io.github.agache41.ormpipes.pipes.accessor;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * <pre>
 * The type Setter.
 * </pre>
 */
public class Setter implements AnnotablePipe<Accessor, Object, ThrowingConsumer<?>> {

    private io.github.agache41.annotator.accessor.Accessor onAccessor;

    private Class<?> onClass;
    private Type type;

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getInputType() {
        return StrongType.of(this.type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return StrongType.of(ThrowingConsumer.class)
                         .parameterizedWith(this.onClass);
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
                          Field onField,
                          Method onMethod,
                          io.github.agache41.annotator.accessor.Accessor<?> onAccessor,
                          String operation) {
        this.onAccessor = onAccessor;
        this.onClass = onClass;
        this.type = this.onAccessor.getGenericType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Object, ThrowingConsumer<?>> function() {
        return value -> inObject -> Setter.this.onAccessor.set(inObject, value);
    }
}
