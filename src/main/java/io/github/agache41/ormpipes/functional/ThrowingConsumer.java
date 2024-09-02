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

import java.util.Objects;
import java.util.function.Consumer;

/**
 * <pre>
 * The interface Throwing consumer.
 * </pre>
 *
 * @param <T> the type parameter
 */
@FunctionalInterface
public interface ThrowingConsumer<T> {
    /**
     * <pre>
     * Wraps a consumer that throws.
     * </pre>
     *
     * @param <T>              the type parameter
     * @param throwingConsumer the throwing consumer
     * @return the consumer
     */
    static <T> Consumer<T> wrap(ThrowingConsumer<T> throwingConsumer) {
        return (t) -> {
            try {
                throwingConsumer.accept(t);
            } catch (Throwable e) {
                throw new WrappedException(e);
            }
        };
    }

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws Throwable the throwable
     */
    void accept(T t) throws Throwable;

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default ThrowingConsumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            this.accept(t);
            after.accept(t);
        };
    }
}