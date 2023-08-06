package com.orm.pipes.pipe;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.registry.PipeRegistry;

public class PipePipes {
    public static class ReadPipe implements AnnotablePipe<Pipe.ofClass, Object, Object> {

        private ThrowingFunction<?, ?> function;
        private AnnotablePipe<?, ?, ?> pipe;

        @Override
        public StrongType getInputType() {
            return this.pipe.getInputType();
        }

        @Override
        public StrongType getOutputType() {
            return this.pipe.getOutputType();
        }

        @Override
        public void configure(Pipe.ofClass cfg) {
            this.pipe = PipeRegistry.buildPipeFrom(cfg.value(), cfg.view(), "read", false);
            this.function = this.pipe.function();
        }

        @Override
        public ThrowingFunction function() {
            return this.function;
        }
    }

    public static class WritePipe implements AnnotablePipe<Pipe.ofClass, Object, Object> {

        private ThrowingFunction<?, ?> function;
        private AnnotablePipe<?, ?, ?> pipe;

        @Override
        public StrongType getInputType() {
            return this.pipe.getInputType();
        }

        @Override
        public StrongType getOutputType() {
            return this.pipe.getOutputType();
        }

        @Override
        public void configure(Pipe.ofClass cfg) {
            this.pipe = PipeRegistry.buildPipeFrom(cfg.value(), cfg.view(), "write", true);
            this.function = this.pipe.function();
        }

        @Override
        public ThrowingFunction function() {
            return this.function;
        }
    }
}
