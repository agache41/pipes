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
