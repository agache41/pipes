package com.orm.pipes.accessor;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Setter implements AnnotablePipe<Accessor, Object, ThrowingConsumer<?>> {

    private com.orm.reflection.accessor.Accessor onAccessor;

    private Class<?> onClass;
    private Type type;

    @Override
    public StrongType getInputType() {
        return StrongType.of(this.type);
    }

    @Override
    public StrongType getOutputType() {
        return StrongType.of(ThrowingConsumer.class)
                         .parameterizedWith(this.onClass);
    }

    @Override
    public void configure(Accessor cfg) {
    }

    @Override
    public void configure(Accessor cfg,
                          Class<?> onClass,
                          Field onField,
                          Method onMethod,
                          com.orm.reflection.accessor.Accessor<?> onAccessor,
                          String operation) {
        this.onAccessor = onAccessor;
        this.onClass = onClass;
        this.type = this.onAccessor.getGenericType();
    }

    @Override
    public ThrowingFunction<Object, ThrowingConsumer<?>> function() {
        return value -> inObject -> Setter.this.onAccessor.set(inObject, value);
    }
}
