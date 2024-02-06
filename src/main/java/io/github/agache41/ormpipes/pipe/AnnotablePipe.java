package io.github.agache41.ormpipes.pipe;

import io.github.agache41.annotator.Helper;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.exception.exceptionHandler.ExceptionHandlerFactory;
import io.github.agache41.ormpipes.pipe.registry.Annotable;
import io.github.agache41.ormpipes.pipe.valueHandler.ValueHandlerFactory;
import org.jboss.logging.Logger;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

/**
 * <pre>
 * The interface Annotable pipe.
 * </pre>
 *
 * @param <A>      the type parameter
 * @param <Input>  the type parameter
 * @param <Output> the type parameter
 */
public interface AnnotablePipe<A extends Annotation, Input, Output> extends Annotable<A>, Pipe<Input, Output> {

    /**
     * <pre>
     * The constant logger.
     * </pre>
     */
    Logger logger = Logger.getLogger(AnnotablePipe.class);

    /**
     * <pre>
     * The constant BROKEN.
     * </pre>
     */
    String BROKEN = "-X->";
    /**
     * <pre>
     * The constant CONNECT.
     * </pre>
     */
    String CONNECT = "--->";


    /**
     * <pre>
     * Gets input type.
     * </pre>
     *
     * @return the input type
     */
    default StrongType getInputType() {
        return StrongType.of(Helper.getActualTypeNameForGenericInterfaceByIndex(this.getClass(), AnnotablePipe.class, 1));

    }

    /**
     * <pre>
     * Gets output type.
     * </pre>
     *
     * @return the output type
     */
    default StrongType getOutputType() {
        return StrongType.of(Helper.getActualTypeNameForGenericInterfaceByIndex(this.getClass(), AnnotablePipe.class, 2));
    }

    /**
     * <pre>
     * Accepts input of boolean.
     * </pre>
     *
     * @param inputType the input type
     * @return the boolean
     */
    default boolean acceptsInputOf(StrongType inputType) {
        boolean result = this.getInputType()
                             .isAssignableFrom(inputType);
        if (!result) {
            logger.debugf(this.getInputType()
                              .getTypeName() + " -//-> " + inputType.getTypeName());
        }
        return result;
    }

    /**
     * <pre>
     * Provides output as boolean.
     * </pre>
     *
     * @param outputType the output type
     * @return the boolean
     */
    default boolean providesOutputAs(StrongType outputType) {
        return outputType.isAssignableFrom(this.getOutputType());
    }

    /**
     * <pre>
     * Conforms to pipe of boolean.
     * </pre>
     *
     * @param inputType  the input type
     * @param outputType the output type
     * @return the boolean
     */
    default boolean conformsToPipeOf(StrongType inputType,
                                     StrongType outputType) {
        return this.acceptsInputOf(inputType) && this.providesOutputAs(outputType);
    }

    /**
     * Binding method of two Pipes
     *
     * @param <NextOutput> the type parameter
     * @param afterPipe    the after pipe
     * @return annotable pipe
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
        logger.debugf("Trying after.stream() coupling %s with %s", this.getDetailedDescription(), streamedAfterPipe.getDetailedDescription());
        // if the stream is accepted
        if (streamedAfterPipe.acceptsInputOf(outputClass)) {
            logger.debugf("Success : Streaming %s to %s", afterPipe.getDetailedDescription(), streamedAfterPipe.getDetailedDescription());
            return this._compose(streamedAfterPipe);
        }

        // if after expects streams
        // convert this to stream
        final AnnotablePipe<A, Input, Output> streamedBeforePipe = (AnnotablePipe<A, Input, Output>) this.stream();
        logger.debugf("Trying this.stream() coupling %s with %s", streamedBeforePipe.getDetailedDescription(), afterPipe.getDetailedDescription());

        // if it is accepted
        if (afterPipe.acceptsInputOf(streamedBeforePipe.getOutputType())) {
            logger.debugf("Success : Streaming %s to %s", streamedBeforePipe.getDetailedDescription(), afterPipe.getDetailedDescription());
            return streamedBeforePipe._compose(afterPipe);
        }

        // try reversing the after
        final AnnotablePipe<A, Output, NextOutput> reversedAfterPipe = (AnnotablePipe<A, Output, NextOutput>) afterPipe.reverse();
        logger.debugf("Trying after.reverse() coupling %s with %s", this.getDetailedDescription(), reversedAfterPipe.getDetailedDescription());
        if (reversedAfterPipe.acceptsInputOf(outputClass)) {
            logger.debugf("Success : Reversing %s to: %s", afterPipe.getDetailedDescription(), reversedAfterPipe.getDetailedDescription());
            return this._compose(reversedAfterPipe);
        }
        // try streaming and reversing the after
        final AnnotablePipe<A, Output, NextOutput> streamedReversedAfterPipe = (AnnotablePipe<A, Output, NextOutput>) afterPipe.stream()
                                                                                                                               .reverse();
        logger.debugf("Trying after.stream().reverse() coupling %s with %s", this.getDetailedDescription(), streamedReversedAfterPipe.getDetailedDescription());

        if (streamedReversedAfterPipe.acceptsInputOf(outputClass)) {
            logger.debugf("Success : Stream&Reversing %s to: %s", afterPipe.getDetailedDescription(), streamedReversedAfterPipe.getDetailedDescription());
            return this._compose(streamedReversedAfterPipe);
        }
        throw new RuntimeException(AnnotablePipe.this.getDescription() + BROKEN + afterPipe.getDescription());
    }

    /**
     * <pre>
     * Compose annotable pipe.
     * </pre>
     *
     * @param <R>       the type parameter
     * @param afterPipe the after pipe
     * @return the annotable pipe
     */
    default <R> AnnotablePipe<A, Input, R> _compose(AnnotablePipe<?, Output, R> afterPipe) {
        logger.debugf("Binding %s with %s", AnnotablePipe.this.getDetailedDescription(), afterPipe.getDetailedDescription());
        return new AnnotablePipe<A, Input, R>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public StrongType getInputType() {
                return AnnotablePipe.this.getInputType();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public StrongType getOutputType() {
                return afterPipe.getOutputType();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void configure(A cfg) {
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public ThrowingFunction<Input, R> function() {
                return afterPipe.function()
                                .compose(AnnotablePipe.this.function());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getDescription() {
                return AnnotablePipe.this.getDescription() + "+" + afterPipe.getDescription();
            }
        };
    }

    /**
     * <pre>
     * Reverse annotable pipe.
     * </pre>
     *
     * @return the annotable pipe
     */
    default AnnotablePipe<A, ThrowingConsumer<Output>, ThrowingConsumer<Input>> reverse() {
        return new AnnotablePipe<A, ThrowingConsumer<Output>, ThrowingConsumer<Input>>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public ThrowingFunction<ThrowingConsumer<Output>, ThrowingConsumer<Input>> function() {
                return AnnotablePipe.this.function()
                                         .reverse();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getDescription() {
                return "Reversed." + AnnotablePipe.this.getDescription();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public StrongType getInputType() {
                return StrongType.of(ThrowingConsumer.class)
                                 .parameterizedWith(AnnotablePipe.this.getOutputType());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public StrongType getOutputType() {
                return StrongType.of(ThrowingConsumer.class)
                                 .parameterizedWith(AnnotablePipe.this.getInputType());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void configure(A cfg) {
                AnnotablePipe.this.configure(cfg);
            }
        };
    }

    /**
     * <pre>
     * Stream annotable pipe.
     * </pre>
     *
     * @return the annotable pipe
     */
    default AnnotablePipe<A, Stream<Input>, Stream<Output>> stream() {
        return new AnnotablePipe<A, Stream<Input>, Stream<Output>>() {

            /**
             * {@inheritDoc}
             */
            @Override
            public ThrowingFunction<Stream<Input>, Stream<Output>> function() {
                return AnnotablePipe.this.function()
                                         .stream();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String getDescription() {
                return "Streamed." + AnnotablePipe.this.getDescription();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public StrongType getInputType() {
                return StrongType.of(Stream.class)
                                 .parameterizedWith(AnnotablePipe.this.getInputType());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public StrongType getOutputType() {
                return StrongType.of(Stream.class)
                                 .parameterizedWith(AnnotablePipe.this.getOutputType());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void configure(A cfg) {
                AnnotablePipe.this.configure(cfg);
            }
        };
    }

    /**
     * <pre>
     * And then annotable pipe.
     * </pre>
     *
     * @param <R>        the type parameter
     * @param beforePipe the before pipe
     * @return the annotable pipe
     */
    default <R> AnnotablePipe<?, ?, ?> andThen(AnnotablePipe<?, R, Input> beforePipe) {
        if (beforePipe == null) {
            return this;
        }
        return beforePipe.compose(this);
    }

    /**
     * <pre>
     * Gets description.
     * </pre>
     *
     * @return the description
     */
    default String getDescription() {
        return this.getClass()
                   .getSimpleName();
    }

    /**
     * <pre>
     * Gets detailed description.
     * </pre>
     *
     * @return the detailed description
     */
    default String getDetailedDescription() {
        return "(" + this.getInputType() + ")-" + this.getDescription() + "->(" + this.getOutputType() + ")";
    }

    /**
     * <pre>
     * Handle exception.
     * </pre>
     *
     * @param cfgClass     the cfg class
     * @param workingClass the working class
     * @param throwable    the throwable
     * @param inputValue   the input value
     */
    default void handleException(Class<A> cfgClass,
                                 Class<? extends AnnotablePipe> workingClass,
                                 Throwable throwable,
                                 Input inputValue) {

        ExceptionHandlerFactory.handleException(cfgClass, workingClass, throwable, inputValue);
    }

    /**
     * <pre>
     * Handle default value output.
     * </pre>
     *
     * @param cfgClass     the cfg class
     * @param workingClass the working class
     * @param outputClass  the output class
     * @param inputValue   the input value
     * @return the output
     */
    default Output handleDefaultValue(Class<? extends Annotation> cfgClass,
                                      Class<? extends AnnotablePipe> workingClass,
                                      Class<Output> outputClass,
                                      Input inputValue) {
        return ValueHandlerFactory.handleValue(cfgClass, workingClass, outputClass, inputValue);
    }

    /**
     * <pre>
     * Default exception value output.
     * </pre>
     *
     * @param cfgClass     the cfg class
     * @param workingClass the working class
     * @param outputClass  the output class
     * @param throwable    the throwable
     * @param inputValue   the input value
     * @return the output
     */
    default Output defaultExceptionValue(Class<? extends Annotation> cfgClass,
                                         Class<? extends AnnotablePipe> workingClass,
                                         Class<Output> outputClass,
                                         Throwable throwable,
                                         Input inputValue) {
        ExceptionHandlerFactory.handleException(cfgClass, workingClass, throwable, inputValue);
        return ValueHandlerFactory.handleValue(cfgClass, workingClass, outputClass, inputValue);
    }
}
