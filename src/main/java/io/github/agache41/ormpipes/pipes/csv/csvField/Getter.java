package io.github.agache41.ormpipes.pipes.csv.csvField;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.annotator.accessor.Accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Getter implements AnnotablePipe<CSVAccessor, Object, Object> {
    private Accessor<?> onAccessor;

    private Type type;

    @Override
    public StrongType getOutputType() {
        return StrongType.of(this.type);
    }

    @Override
    public void configure(CSVAccessor cfg) {
    }

    @Override
    public void configure(CSVAccessor cfg,
                          Class<?> onClass,
                          Field onField,
                          Method onMethod,
                          Accessor<?> onAccessor,
                          String operation) {
        this.onAccessor = onAccessor;
        this.type = onAccessor.getGenericType();
    }

    @Override
    public ThrowingFunction<Object, Object> function() {
        return this.onAccessor::get;
    }
}
