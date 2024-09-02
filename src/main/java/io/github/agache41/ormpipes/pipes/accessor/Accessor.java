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

package io.github.agache41.ormpipes.pipes.accessor;


import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.lang.annotation.*;

/**
 * <pre>
 * The interface Accessor.
 * </pre>
 */
@Repeatable(Accessors.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends({DualPipe.class})
public @interface Accessor {

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
    Class<? extends AnnotablePipe<Accessor, ?, ThrowingConsumer<?>>> read() default Setter.class;

    /**
     * <pre>
     * Write class.
     * </pre>
     *
     * @return the class
     */
    Class<? extends AnnotablePipe<Accessor, ?, ?>> write() default Getter.class;

    /**
     * <pre>
     * View string.
     * </pre>
     *
     * @return the string
     */
    String view() default Constants.DEFAULT;
}
