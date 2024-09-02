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

package io.github.agache41.ormpipes.pipes.base.format;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;

import java.lang.annotation.Annotation;

/**
 * <pre>
 * The type Abstract format.
 * </pre>
 *
 * @param <A>     the type parameter
 * @param <Input> the type parameter
 */
public abstract class AbstractFormat<A extends Annotation, Input> extends AbstractFormatter<A, Input, String> implements AnnotablePipe<A, Input, String> {

    /**
     * <pre>
     * The Function.
     * </pre>
     */
    protected ThrowingFunction<Input, String> function;

    /**
     * {@inheritDoc}
     */
    @Override
    public StrongType getOutputType() {
        return TypeString.strongType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(A cfg) {
        super.configure(cfg);
        if (this.simple) {
            this.function = Object::toString;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThrowingFunction<Input, String> function() {
        return this.function.nullSafe(this.nullSafe)
                            .blankOutputSafe(this.blankSafe);


    }
}
