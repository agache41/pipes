package io.github.agache41.ormpipes.pipes.csv.csvFile.impl;

import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalEntry;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.csv.csvField.CSVAccessor;
import io.github.agache41.annotator.accessor.Accessor;

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
