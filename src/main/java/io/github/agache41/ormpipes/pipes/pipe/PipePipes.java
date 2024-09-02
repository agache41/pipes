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

package io.github.agache41.ormpipes.pipes.pipe;

import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.registry.PipeRegistry;

/**
 * <pre>
 * The type Pipe pipes.
 * </pre>
 */
public class PipePipes {
    /**
     * <pre>
     * The type Read pipe.
     * </pre>
     */
    public static class ReadPipe implements AnnotablePipe<Pipe.ofClass, Object, Object> {

        private ThrowingFunction<?, ?> function;
        private AnnotablePipe<?, ?, ?> pipe;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return this.pipe.getInputType();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return this.pipe.getOutputType();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Pipe.ofClass cfg) {
            this.pipe = PipeRegistry.buildPipeFrom(cfg.value(), cfg.view(), "read", false);
            this.function = this.pipe.function();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Write pipe.
     * </pre>
     */
    public static class WritePipe implements AnnotablePipe<Pipe.ofClass, Object, Object> {

        private ThrowingFunction<?, ?> function;
        private AnnotablePipe<?, ?, ?> pipe;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return this.pipe.getInputType();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return this.pipe.getOutputType();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(Pipe.ofClass cfg) {
            this.pipe = PipeRegistry.buildPipeFrom(cfg.value(), cfg.view(), "write", true);
            this.function = this.pipe.function();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction function() {
            return this.function;
        }
    }
}
