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

package io.github.agache41.ormpipes.pipes.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Fields.
 * </pre>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Fields {
    /**
     * <pre>
     * Value field [ ].
     * </pre>
     *
     * @return the field [ ]
     */
    Field[] value();

    /**
     * <pre>
     * The interface For each field havings.
     * </pre>
     */
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface ForEachFieldHavings {
        /**
         * <pre>
         * Value field . for each annotated with [ ].
         * </pre>
         *
         * @return the field . for each annotated with [ ]
         */
        Field.forEachAnnotatedWith[] value();
    }
}
