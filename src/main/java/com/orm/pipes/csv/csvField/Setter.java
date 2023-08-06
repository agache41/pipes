package com.orm.pipes.csv.csvField;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.reflection.accessor.Accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Setter implements AnnotablePipe<CSVAccessor, Object, ThrowingConsumer<?>> {

    private Accessor onAccessor;
    private Type type;

    @Override
    public StrongType getInputType() {
        return StrongType.of(this.type);
    }

    @Override
    public void configure(CSVAccessor cfg) {
    }

    @Override
    public void configure(CSVAccessor cfg, Class<?> onClass, Field onField, Method onMethod, Accessor<?> onAccessor, String operation) {
        this.onAccessor = onAccessor;
        this.type = onAccessor.getGenericType();
    }

    @Override
    public ThrowingFunction<Object, ThrowingConsumer<?>> function() {
        return value -> inObject -> Setter.this.onAccessor.set(inObject, value);
    }
}
