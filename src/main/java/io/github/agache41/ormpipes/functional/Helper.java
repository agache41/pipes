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

import java.lang.reflect.Constructor;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * <pre>
 * The type Helper.
 * </pre>
 */
public class Helper {
    /**
     * <pre>
     * Throwing merger binary operator.
     * </pre>
     *
     * @param <T>     the type parameter
     * @param message the message
     * @return the binary operator
     */
    public static <T> BinaryOperator<T> throwingMerger(String message) {
        return (u, v) -> {
            throw new IllegalStateException(String.format(message + "%s", u));
        };
    }

    /**
     * <pre>
     * Constructor supplier.
     * </pre>
     *
     * @param <T>     the type parameter
     * @param ofClass the of class
     * @return the supplier
     */
    public static <T> Supplier<T> constructor(Class<T> ofClass) {
        try {
            final Constructor<T> constructor = ofClass.getConstructor();
            return () -> {
                try {
                    return constructor.newInstance();
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            };
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(" Class " + ofClass.getSimpleName() + " does not have an empty constructor!", e);
        }
    }
}
