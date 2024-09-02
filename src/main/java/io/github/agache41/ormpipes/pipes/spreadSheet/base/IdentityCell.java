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

package io.github.agache41.ormpipes.pipes.spreadSheet.base;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import org.apache.poi.ss.usermodel.Cell;

import java.lang.annotation.Annotation;

/**
 * <pre>
 * The type Identity cell.
 * </pre>
 */
public class IdentityCell implements AnnotablePipe<Annotation, Cell, Cell> {
    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return StrongType.of(Cell.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getInputType() {
        return StrongType.of(Cell.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(Annotation cfg) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Cell, Cell> function() {
        return ThrowingFunction.identity();
    }
}
