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

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalFile;

import java.lang.annotation.*;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;

/**
 * <pre>
 * The interface Field.
 * </pre>
 */
@Repeatable(Fields.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Field {
    /**
     * <pre>
     * The interface For each annotated with.
     * </pre>
     */
    @Repeatable(Fields.ForEachFieldHavings.class)
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, OrthogonalFile.class})
    @interface forEachAnnotatedWith {
        /**
         * <pre>
         * Value class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends Annotation> value();

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
        Class<? extends AnnotablePipe<forEachAnnotatedWith, ?, ?>> read() default FieldPipes.ReadForEachAnnotatedWith.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<forEachAnnotatedWith, ?, ThrowingConsumer<?>>> write() default FieldPipes.WriteForEachAnnotatedWith.class;

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