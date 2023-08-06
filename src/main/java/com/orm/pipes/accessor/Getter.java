package com.orm.pipes.accessor;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Getter implements AnnotablePipe<Accessor, Object, Object> {
    private com.orm.reflection.accessor.Accessor<?> onAccessor;

    private Class<?> onClass;

    private Type type;

    @Override
    public StrongType getOutputType() {
        return StrongType.of(this.type);
    }

    @Override
    public StrongType getInputType() {
        return StrongType.of(this.onClass);
    }

    @Override
    public void configure(Accessor cfg) {
    }

    @Override
    public void configure(Accessor cfg, Class<?> onClass, java.lang.reflect.Field onField, Method onMethod, com.orm.reflection.accessor.Accessor<?> onAccessor, String operation) {
        this.onAccessor = onAccessor;
        this.onClass = onClass;
        this.type = onAccessor.getGenericType();
    }

    @Override
    public ThrowingFunction<Object, Object> function() {
        return this.onAccessor::get;
    }
}
