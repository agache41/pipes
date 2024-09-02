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

package io.github.agache41.ormpipes.functional;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * <pre>
 * The interface Throwing function.
 * </pre>
 *
 * @param <Input>  the type parameter
 * @param <Output> the type parameter
 */
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
     * @param <T>              the type parameter
     * @param <R>              the type parameter
     * @param throwingFunction the throwing function
     * @return function
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

    /**
     * <pre>
     * Null safe throwing function.
     * </pre>
     *
     * @param <T>              the type parameter
     * @param <R>              the type parameter
     * @param throwingFunction the throwing function
     * @return the throwing function
     */
    static <T, R> ThrowingFunction<T, R> nullSafe(ThrowingFunction<T, R> throwingFunction) {
        return input -> {
            if (null == input) {
                return null;
            }
            return throwingFunction.apply(input);
        };
    }

    /**
     * <pre>
     * Blank safe throwing function.
     * </pre>
     *
     * @param <R>              the type parameter
     * @param throwingFunction the throwing function
     * @return the throwing function
     */
    static <R> ThrowingFunction<String, R> blankSafe(ThrowingFunction<String, R> throwingFunction) {
        return input -> {
            if (StringUtils.isBlank(input)) {
                return null;
            }
            return throwingFunction.apply(input);
        };
    }


    /**
     * Applies this function to the given argument.
     *
     * @param input the function argument
     * @return the function result
     * @throws Throwable the throwable
     */
    Output apply(Input input) throws Throwable;

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>    the type of input to the {@code before} function, and to the               composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before} function and then applies this function
     * @throws NullPointerException if before is null
     * @see #andThen(ThrowingFunction)
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
     * @param <V>   the type of output of the {@code after} function, and of the              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     * @throws NullPointerException if after is null
     * @see #compose(ThrowingFunction)
     */
    default <V> ThrowingFunction<Input, V> andThen(ThrowingFunction<? super Output, ? extends V> after) {
        Objects.requireNonNull(after);
        return (Input input) -> after.apply(this.apply(input));
    }

    /**
     * Returns a reversed Function Using the Consumer Pattern
     *
     * @return throwing function
     */
    default ThrowingFunction<ThrowingConsumer<Output>, ThrowingConsumer<Input>> reverse() {
        return consumerOutput -> input -> consumerOutput.accept(ThrowingFunction.this.apply(input));
    }

    /**
     * <pre>
     * Stream throwing function.
     * </pre>
     *
     * @return the throwing function
     */
    default ThrowingFunction<Stream<Input>, Stream<Output>> stream() {
        return inputStream -> inputStream.map(wrap(this::apply));
    }

    /**
     * Wraps the ThrowingFunction into a Function, by wrapping the exception in a WrapperException
     *
     * @return function
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

    /**
     * <pre>
     * Null safe throwing function.
     * </pre>
     *
     * @return the throwing function
     */
    default ThrowingFunction<Input, Output> nullSafe() {
        return input -> {
            if (null == input) {
                return null;
            }
            return this.apply(input);
        };
    }

    /**
     * <pre>
     * Null safe throwing function.
     * </pre>
     *
     * @param nullSafe the null safe
     * @return the throwing function
     */
    default ThrowingFunction<Input, Output> nullSafe(boolean nullSafe) {
        if (nullSafe) {
            return this.nullSafe();
        }
        return this;
    }


    /**
     * <pre>
     * Blank input safe throwing function.
     * </pre>
     *
     * @return the throwing function
     */
    default ThrowingFunction<Input, Output> blankInputSafe() {
        return input -> {
            if (StringUtils.isBlank((String) input)) {
                return null;
            }
            return this.apply(input);
        };
    }

    /**
     * <pre>
     * Blank input safe throwing function.
     * </pre>
     *
     * @param blankInputSafe the blank input safe
     * @return the throwing function
     */
    default ThrowingFunction<Input, Output> blankInputSafe(boolean blankInputSafe) {
        if (blankInputSafe) {
            return this.blankInputSafe();
        }
        return this;
    }

    /**
     * <pre>
     * Blank output safe throwing function.
     * </pre>
     *
     * @return the throwing function
     */
    default ThrowingFunction<Input, Output> blankOutputSafe() {
        return input -> {
            if (input == null) {
                return (Output) "";
            }
            return this.apply(input);
        };
    }

    /**
     * <pre>
     * Blank output safe throwing function.
     * </pre>
     *
     * @param blankOutputSafe the blank output safe
     * @return the throwing function
     */
    default ThrowingFunction<Input, Output> blankOutputSafe(boolean blankOutputSafe) {
        if (blankOutputSafe) {
            return this.blankOutputSafe();
        }
        return this;
    }

    /**
     * Wraps the ThrowingFunction into a Function that does not throw any exceptions
     * In case an exception is thrown, null is returned
     *
     * @return throwing function
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

    /**
     * <pre>
     * No exception throwing function.
     * </pre>
     *
     * @param noException the no exception
     * @return the throwing function
     */
    default ThrowingFunction<Input, Output> noException(boolean noException) {
        if (noException) {
            return this.noException();
        }
        return this;
    }
}