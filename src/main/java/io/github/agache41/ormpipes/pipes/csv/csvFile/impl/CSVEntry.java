/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.agache41.ormpipes.pipes.csv.csvFile.impl;

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalEntry;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.NamingMethod;
import io.github.agache41.ormpipes.pipes.base.othogonal.enums.PositionMethod;
import io.github.agache41.ormpipes.pipes.csv.csvField.CSVAccessor;

import java.lang.annotation.Annotation;

/**
 * <pre>
 * The type Csv entry.
 * </pre>
 */
public class CSVEntry extends OrthogonalEntry<CSVAccessor, String, ThrowingConsumer<Object>, Object, String> {
    /**
     * <pre>
     * Instantiates a new Csv entry.
     * </pre>
     *
     * @param name     the name
     * @param position the position
     */
    public CSVEntry(String name,
                    int position) {
        super(name, position);
        this.readPipe = new AnnotablePipe<Annotation, String, ThrowingConsumer<Object>>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public ThrowingFunction<String, ThrowingConsumer<Object>> function() {
                return s -> o -> {
                };
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void configure(Annotation cfg) {
            }
        };

        this.writePipe = new AnnotablePipe<Annotation, Object, String>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public ThrowingFunction<Object, String> function() {
                return s -> "";
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void configure(Annotation cfg) {
            }
        };
    }

    /**
     * <pre>
     * Instantiates a new Csv entry.
     * </pre>
     *
     * @param accessor       the accessor
     * @param namingMethod   the naming method
     * @param positionMethod the position method
     */
    public CSVEntry(Accessor<?> accessor,
                    NamingMethod namingMethod,
                    PositionMethod positionMethod) {
        super(CSVAccessor.class, accessor, namingMethod, positionMethod);
    }
}
