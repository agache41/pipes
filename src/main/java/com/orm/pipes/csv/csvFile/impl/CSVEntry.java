package com.orm.pipes.csv.csvFile.impl;

import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipes.base.othogonal.OrthogonalEntry;
import com.orm.pipes.base.othogonal.enums.NamingMethod;
import com.orm.pipes.base.othogonal.enums.PositionMethod;
import com.orm.pipes.csv.csvField.CSVAccessor;
import com.orm.reflection.accessor.Accessor;

import java.lang.annotation.Annotation;

public class CSVEntry extends OrthogonalEntry<CSVAccessor, String, ThrowingConsumer<Object>, Object, String> {
    public CSVEntry(String name, int position) {
        super(name, position);
        this.readPipe = new AnnotablePipe<Annotation, String, ThrowingConsumer<Object>>() {
            @Override
            public ThrowingFunction<String, ThrowingConsumer<Object>> function() {
                return s -> o -> {
                };
            }

            @Override
            public void configure(Annotation cfg) {
            }
        };

        this.writePipe = new AnnotablePipe<Annotation, Object, String>() {
            @Override
            public ThrowingFunction<Object, String> function() {
                return s -> "";
            }

            @Override
            public void configure(Annotation cfg) {
            }
        };
    }

    public CSVEntry(Accessor<?> accessor, NamingMethod namingMethod, PositionMethod positionMethod) {
        super(CSVAccessor.class, accessor, namingMethod, positionMethod);
    }
}
