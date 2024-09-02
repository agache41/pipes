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

package io.github.agache41.ormpipes.pipes.base.format;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * The interface Type format.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeFormat {
    /**
     * <pre>
     * Format string.
     * </pre>
     *
     * @return the string
     */
    String format() default "";

    /**
     * <pre>
     * Language tag string.
     * </pre>
     *
     * @return the string
     */
    String languageTag() default "";

    /**
     * <pre>
     * Simple boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean simple() default false;

    /**
     * <pre>
     * Null safe boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean nullSafe() default true;

    /**
     * <pre>
     * Blank safe boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean blankSafe() default true;

    /**
     * <pre>
     * No exception boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean noException() default true;
}
