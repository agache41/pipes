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

package io.github.agache41.ormpipes.pipes.typeObject;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;

/**
 * <pre>
 * The type Object pipes.
 * </pre>
 */
public class ObjectPipes {

    /**
     * <pre>
     * The type To string.
     * </pre>
     */
    public static class ToString implements AnnotablePipe<TypeObject.toString, Object, String> {
        private boolean nullSafe;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeObject.toString cfg) {
            this.nullSafe = cfg.nullSafe();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Object, String> function() {
            if (this.nullSafe) {
                return ThrowingFunction.nullSafe(Object::toString);
            }
            return Object::toString;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeObject.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeString.strongType;
        }
    }
}
