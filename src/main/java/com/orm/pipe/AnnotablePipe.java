package com.orm.pipe;

import com.orm.functional.StrongType;
import com.orm.functional.ThrowingConsumer;
import com.orm.functional.ThrowingFunction;
import com.orm.pipe.exception.exceptionHandler.ExceptionHandlerFactory;
import com.orm.pipe.valueHandler.ValueHandlerFactory;
import com.orm.reflection.Helper;
import com.orm.reflection.registry.Annotable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

public interface AnnotablePipe<A extends Annotation, Input, Output> extends Annotable<A>, Pipe<Input, Output> {

    Logger logger = LogManager.getLogger(AnnotablePipe.class);

    String BROKEN = "-X->";
    String CONNECT = "--->";


    default StrongType getInputType() {
        return StrongType.of(Helper.getActualTypeNameForGenericInterfaceByIndex(this.getClass(), AnnotablePipe.class, 1));

    }

    default StrongType getOutputType() {
        return StrongType.of(Helper.getActualTypeNameForGenericInterfaceByIndex(this.getClass(), AnnotablePipe.class, 2));
    }

    default boolean acceptsInputOf(StrongType inputType) {
        boolean result = this.getInputType()
                             .isAssignableFrom(inputType);
        if (!result)
            logger.debug(this.getInputType()
                             .getTypeName() + " -//-> " + inputType.getTypeName());
        return result;
    }

    default boolean providesOutputAs(StrongType outputType) {
        return outputType.isAssignableFrom(this.getOutputType());
    }

    default boolean conformsToPipeOf(StrongType inputType, StrongType outputType) {
        return this.acceptsInputOf(inputType) && this.providesOutputAs(outputType);
    }

    /**
     * Binding method of two Pipes
     *
     * @param afterPipe
     * @param <NextOutput>
     * @return
     */
    default <NextOutput> AnnotablePipe<A, ?, ?> compose(AnnotablePipe<?, Output, NextOutput> afterPipe) {
        if (afterPipe == null) {
            return this;
        }
        StrongType outputClass = AnnotablePipe.this.getOutputType();
        if (afterPipe.acceptsInputOf(outputClass)) {
            // default situation
            return this._compose(afterPipe);
        }
        // if we produce a stream
        // convert the after to stream
        final AnnotablePipe<?, Output, NextOutput> streamedAfterPipe = (AnnotablePipe<?, Output, NextOutput>) afterPipe.stream();
        logger.debug("Trying after.stream() coupling {} with {}", this.getDetailedDescription(), streamedAfterPipe.getDetailedDescription());
        // if the stream is accepted
        if (streamedAfterPipe.acceptsInputOf(outputClass)) {
            logger.debug("Success : Streaming {} to {}", afterPipe.getDetailedDescription(), streamedAfterPipe.getDetailedDescription());
            return this._compose(streamedAfterPipe);
        }

        // if after expects streams
        // convert this to stream
        final AnnotablePipe<A, Input, Output> streamedBeforePipe = (AnnotablePipe<A, Input, Output>) this.stream();
        logger.debug("Trying this.stream() coupling {} with {}", streamedBeforePipe.getDetailedDescription(), afterPipe.getDetailedDescription());

        // if it is accepted
        if (afterPipe.acceptsInputOf(streamedBeforePipe.getOutputType())) {
            logger.debug("Success : Streaming {} to {}", streamedBeforePipe.getDetailedDescription(), afterPipe.getDetailedDescription());
            return streamedBeforePipe._compose(afterPipe);
        }

        // try reversing the after
        final AnnotablePipe<A, Output, NextOutput> reversedAfterPipe = (AnnotablePipe<A, Output, NextOutput>) afterPipe.reverse();
        logger.debug("Trying after.reverse() coupling {} with {}", this.getDetailedDescription(), reversedAfterPipe.getDetailedDescription());
        if (reversedAfterPipe.acceptsInputOf(outputClass)) {
            logger.debug("Success : Reversing {} to: {}", afterPipe.getDetailedDescription(), reversedAfterPipe.getDetailedDescription());
            return this._compose(reversedAfterPipe);
        }
        // try streaming and reversing the after
        final AnnotablePipe<A, Output, NextOutput> streamedReversedAfterPipe = (AnnotablePipe<A, Output, NextOutput>) afterPipe.stream()
                                                                                                                               .reverse();
        logger.debug("Trying after.stream().reverse() coupling {} with {}", this.getDetailedDescription(), streamedReversedAfterPipe.getDetailedDescription());

        if (streamedReversedAfterPipe.acceptsInputOf(outputClass)) {
            logger.debug("Success : Stream&Reversing {} to: {}", afterPipe.getDetailedDescription(), streamedReversedAfterPipe.getDetailedDescription());
            return this._compose(streamedReversedAfterPipe);
        }
        throw new RuntimeException(AnnotablePipe.this.getDescription() + BROKEN + afterPipe.getDescription());
    }

    default <R> AnnotablePipe<A, Input, R> _compose(AnnotablePipe<?, Output, R> afterPipe) {
        logger.debug("Binding {} with {}", AnnotablePipe.this.getDetailedDescription(), afterPipe.getDetailedDescription());
        return new AnnotablePipe<A, Input, R>() {
            @Override
            public StrongType getInputType() {
                return AnnotablePipe.this.getInputType();
            }

            @Override
            public StrongType getOutputType() {
                return afterPipe.getOutputType();
            }

            @Override
            public void configure(A cfg) {
            }

            @Override
            public ThrowingFunction<Input, R> function() {
                return afterPipe.function()
                                .compose(AnnotablePipe.this.function());
            }

            @Override
            public String getDescription() {
                return AnnotablePipe.this.getDescription() + "+" + afterPipe.getDescription();
            }
        };
    }

    default AnnotablePipe<A, ThrowingConsumer<Output>, ThrowingConsumer<Input>> reverse() {
        return new AnnotablePipe<A, ThrowingConsumer<Output>, ThrowingConsumer<Input>>() {
            @Override
            public ThrowingFunction<ThrowingConsumer<Output>, ThrowingConsumer<Input>> function() {
                return AnnotablePipe.this.function()
                                         .reverse();
            }

            @Override
            public String getDescription() {
                return "Reversed." + AnnotablePipe.this.getDescription();
            }

            @Override
            public StrongType getInputType() {
                return StrongType.of(ThrowingConsumer.class)
                                 .parameterizedWith(AnnotablePipe.this.getOutputType());
            }

            @Override
            public StrongType getOutputType() {
                return StrongType.of(ThrowingConsumer.class)
                                 .parameterizedWith(AnnotablePipe.this.getInputType());
            }

            @Override
            public void configure(A cfg) {
                AnnotablePipe.this.configure(cfg);
            }
        };
    }

    default AnnotablePipe<A, Stream<Input>, Stream<Output>> stream() {
        return new AnnotablePipe<A, Stream<Input>, Stream<Output>>() {

            @Override
            public ThrowingFunction<Stream<Input>, Stream<Output>> function() {
                return AnnotablePipe.this.function()
                                         .stream();
            }

            @Override
            public String getDescription() {
                return "Streamed." + AnnotablePipe.this.getDescription();
            }

            @Override
            public StrongType getInputType() {
                return StrongType.of(Stream.class)
                                 .parameterizedWith(AnnotablePipe.this.getInputType());
            }

            @Override
            public StrongType getOutputType() {
                return StrongType.of(Stream.class)
                                 .parameterizedWith(AnnotablePipe.this.getOutputType());
            }

            @Override
            public void configure(A cfg) {
                AnnotablePipe.this.configure(cfg);
            }
        };
    }

    default <R> AnnotablePipe<?, ?, ?> andThen(AnnotablePipe<?, R, Input> beforePipe) {
        if (beforePipe == null) {
            return this;
        }
        return beforePipe.compose(this);
    }

    default String getDescription() {
        return this.getClass()
                   .getSimpleName();
    }

    default String getDetailedDescription() {
        return "(" + this.getInputType() + ")-" + this.getDescription() + "->(" + this.getOutputType() + ")";
    }

    default void handleException(Class<A> cfgClass, Class<? extends AnnotablePipe> workingClass, Throwable throwable, Input inputValue) {

        ExceptionHandlerFactory.handleException(cfgClass, workingClass, throwable, inputValue);
    }

    default Output handleDefaultValue(Class<? extends Annotation> cfgClass, Class<? extends AnnotablePipe> workingClass, Class<Output> outputClass, Input inputValue) {
        return ValueHandlerFactory.handleValue(cfgClass, workingClass, outputClass, inputValue);
    }

    default Output defaultExceptionValue(Class<? extends Annotation> cfgClass, Class<? extends AnnotablePipe> workingClass, Class<Output> outputClass, Throwable throwable, Input inputValue) {
        ExceptionHandlerFactory.handleException(cfgClass, workingClass, throwable, inputValue);
        return ValueHandlerFactory.handleValue(cfgClass, workingClass, outputClass, inputValue);
    }
}
