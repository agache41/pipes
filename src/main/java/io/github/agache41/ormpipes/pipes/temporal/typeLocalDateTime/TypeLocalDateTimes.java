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

package io.github.agache41.ormpipes.pipes.temporal.typeLocalDateTime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type local date times.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeLocalDateTimes {
    /**
     * <pre>
     * Value type local date time [ ].
     * </pre>
     *
     * @return the type local date time [ ]
     */
    TypeLocalDateTime[] value();

    /**
     * <pre>
     * The interface News.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface News {
        /**
         * <pre>
         * Value type local date time . new [ ].
         * </pre>
         *
         * @return the type local date time . new [ ]
         */
        TypeLocalDateTime.New[] value();
    }

    /**
     * <pre>
     * The interface Nows.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface nows {
        /**
         * <pre>
         * Value type local date time . now [ ].
         * </pre>
         *
         * @return the type local date time . now [ ]
         */
        TypeLocalDateTime.now[] value();
    }

    /**
     * <pre>
     * The interface Cell values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface CellValues {
        /**
         * <pre>
         * Value type local date time . cell value [ ].
         * </pre>
         *
         * @return the type local date time . cell value [ ]
         */
        TypeLocalDateTime.cellValue[] value();
    }
}
