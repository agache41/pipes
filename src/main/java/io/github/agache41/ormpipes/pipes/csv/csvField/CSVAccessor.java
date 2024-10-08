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

package io.github.agache41.ormpipes.pipes.csv.csvField;


import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.Orthogonal;

import java.lang.annotation.*;

import static io.github.agache41.annotator.accessor.Accessor.NO_POSITION;


/**
 * <pre>
 * The interface Csv accessor.
 * </pre>
 */
@Repeatable(CSVAccessors.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends({DualPipe.class, Orthogonal.class})
public @interface CSVAccessor {
    /**
     * <pre>
     * Name string.
     * </pre>
     *
     * @return the string
     */
    String name() default "";

    /**
     * <pre>
     * Position int.
     * </pre>
     *
     * @return the int
     */
    int position() default NO_POSITION;

    /**
     * <pre>
     * Required boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean required() default false;

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
    Class<? extends AnnotablePipe<CSVAccessor, ?, ThrowingConsumer<?>>> read() default Setter.class;

    /**
     * <pre>
     * Write class.
     * </pre>
     *
     * @return the class
     */
    Class<? extends AnnotablePipe<CSVAccessor, ?, ?>> write() default Getter.class;

    /**
     * <pre>
     * View string.
     * </pre>
     *
     * @return the string
     */
    String view() default Constants.DEFAULT;
}
