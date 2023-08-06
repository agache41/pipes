package com.orm.functional;

import java.util.function.Supplier;

/**
 * Represents a supplier of results.
 *
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #get()}.
 *
 * @param <T> the type of results supplied by this supplier
 * @since 1.8
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {

    static <T> Supplier<T> wrap(ThrowingSupplier<T> throwingSupplier) {
        return () -> {
            try {
                return throwingSupplier.get();
            } catch (Throwable e) {
                throw new WrappedException(e);
            }
        };
    }

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Throwable;
}
