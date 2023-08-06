package com.orm.functional;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T> {
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
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
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