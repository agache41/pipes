package com.orm.functional;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

@FunctionalInterface
public interface ThrowingFunction<Input, Output> {
    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    static <T> ThrowingFunction<T, T> identity() {
        return t -> t;
    }

    /**
     * Wraps a ThrowingFunction into a Function, by wrapping the exception in a WrapperException
     *
     * @param throwingFunction
     * @param <T>
     * @param <R>
     * @return
     */
    static <T, R> Function<T, R> wrap(ThrowingFunction<T, R> throwingFunction) {
        return t -> {
            try {
                return throwingFunction.apply(t);
            } catch (Throwable e) {
                throw new WrappedException(e);
            }
        };
    }

    static <T, R> ThrowingFunction<T, R> nullSafe(ThrowingFunction<T, R> throwingFunction) {
        return input -> {
            if (null == input) return null;
            return throwingFunction.apply(input);
        };
    }

    static <R> ThrowingFunction<String, R> blankSafe(ThrowingFunction<String, R> throwingFunction) {
        return input -> {
            if (StringUtils.isBlank(input)) return null;
            return throwingFunction.apply(input);
        };
    }


    /**
     * Applies this function to the given argument.
     *
     * @param input the function argument
     * @return the function result
     */
    Output apply(Input input) throws Throwable;

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>    the type of input to the {@code before} function, and to the
     *               composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     * @See #andThen(ThrowingFunction)
     */
    default <V> ThrowingFunction<V, Output> compose(ThrowingFunction<? super V, ? extends Input> before) {
        Objects.requireNonNull(before);
        return (V v) -> this.apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     * @See #compose(ThrowingFunction)
     */
    default <V> ThrowingFunction<Input, V> andThen(ThrowingFunction<? super Output, ? extends V> after) {
        Objects.requireNonNull(after);
        return (Input input) -> after.apply(this.apply(input));
    }

    /**
     * Returns a reversed Function Using the Consumer Pattern
     *
     * @return
     */
    default ThrowingFunction<ThrowingConsumer<Output>, ThrowingConsumer<Input>> reverse() {
        return consumerOutput -> input -> consumerOutput.accept(ThrowingFunction.this.apply(input));
    }

    default ThrowingFunction<Stream<Input>, Stream<Output>> stream() {
        return inputStream -> inputStream.map(wrap(this::apply));
    }

    /**
     * Wraps the ThrowingFunction into a Function, by wrapping the exception in a WrapperException
     *
     * @return
     */
    default Function<Input, Output> wrap() {
        return t -> {
            try {
                return this.apply(t);
            } catch (Throwable e) {
                throw new WrappedException(e);
            }
        };
    }

    default ThrowingFunction<Input, Output> nullSafe() {
        return input -> {
            if (null == input) return null;
            return this.apply(input);
        };
    }

    default ThrowingFunction<Input, Output> nullSafe(boolean nullSafe) {
        if (nullSafe)
            return this.nullSafe();
        return this;
    }


    default ThrowingFunction<Input, Output> blankInputSafe() {
        return input -> {
            if (StringUtils.isBlank((String) input)) return null;
            return this.apply(input);
        };
    }

    default ThrowingFunction<Input, Output> blankInputSafe(boolean blankInputSafe) {
        if (blankInputSafe)
            return this.blankInputSafe();
        return this;
    }

    default ThrowingFunction<Input, Output> blankOutputSafe() {
        return input -> {
            if (input == null) return (Output) "";
            return this.apply(input);
        };
    }

    default ThrowingFunction<Input, Output> blankOutputSafe(boolean blankOutputSafe) {
        if (blankOutputSafe)
            return this.blankOutputSafe();
        return this;
    }

    /**
     * Wraps the ThrowingFunction into a Function that does not throw any exceptions
     * In case an exception is thrown, null is returned
     *
     * @return
     */
    default ThrowingFunction<Input, Output> noException() {
        return t -> {
            try {
                return this.apply(t);
            } catch (Throwable e) {
                return null;
            }
        };
    }

    default ThrowingFunction<Input, Output> noException(boolean noException) {
        if (noException)
            return this.noException();
        return this;
    }
}