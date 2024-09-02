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

package io.github.agache41.ormpipes.pipes.typeObject;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.lang.annotation.*;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;

/**
 * <pre>
 * The interface Type object.
 * </pre>
 */
@Repeatable(TypeObjects.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Extends(DualPipe.class)
public @interface TypeObject {
    /**
     * <pre>
     * The constant strongType.
     * </pre>
     */
    StrongType strongType = StrongType.of(Object.class);

    /**
     * <pre>
     * The interface To string.
     * </pre>
     */
    @Repeatable(TypeObjects.toUpperCases.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface toString {
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
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<toString, ?, String>> read() default ObjectPipes.ToString.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<toString, ?, String>> write() default ObjectPipes.ToString.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;
    }
}
