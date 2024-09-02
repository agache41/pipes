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

package io.github.agache41.ormpipes.pipes.csv.csvField;

import io.github.agache41.annotator.accessor.Accessor;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * <pre>
 * The type Getter.
 * </pre>
 */
public class Getter implements AnnotablePipe<CSVAccessor, Object, Object> {
    private Accessor<?> onAccessor;

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
    public void configure(CSVAccessor cfg) {
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Object, Object> function() {
        return this.onAccessor::get;
    }
}
