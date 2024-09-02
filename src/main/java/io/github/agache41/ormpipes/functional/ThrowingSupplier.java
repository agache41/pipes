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

    /**
     * <pre>
     * Wrap supplier.
     * </pre>
     *
     * @param <T>              the type parameter
     * @param throwingSupplier the throwing supplier
     * @return the supplier
     */
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
     * @throws Throwable the throwable
     */
    T get() throws Throwable;
}
