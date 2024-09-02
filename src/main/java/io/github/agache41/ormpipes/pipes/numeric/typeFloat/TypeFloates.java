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

package io.github.agache41.ormpipes.pipes.numeric.typeFloat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type floates.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeFloates {
    /**
     * <pre>
     * Value type float [ ].
     * </pre>
     *
     * @return the type float [ ]
     */
    TypeFloat[] value();

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
         * Value type float . new [ ].
         * </pre>
         *
         * @return the type float . new [ ]
         */
        TypeFloat.New[] value();
    }

    /**
     * <pre>
     * The interface Value ofs.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface valueOfs {
        /**
         * <pre>
         * Value type float . value [ ].
         * </pre>
         *
         * @return the type float . value [ ]
         */
        TypeFloat.value[] value();
    }

    /**
     * <pre>
     * The interface Short values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface shortValues {
        /**
         * <pre>
         * Value type float . short value [ ].
         * </pre>
         *
         * @return the type float . short value [ ]
         */
        TypeFloat.shortValue[] value();
    }

    /**
     * <pre>
     * The interface Int values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface intValues {
        /**
         * <pre>
         * Value type float . int value [ ].
         * </pre>
         *
         * @return the type float . int value [ ]
         */
        TypeFloat.intValue[] value();
    }

    /**
     * <pre>
     * The interface Long values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface longValues {
        /**
         * <pre>
         * Value type float . long value [ ].
         * </pre>
         *
         * @return the type float . long value [ ]
         */
        TypeFloat.longValue[] value();
    }

    /**
     * <pre>
     * The interface Double values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface doubleValues {
        /**
         * <pre>
         * Value type float . double value [ ].
         * </pre>
         *
         * @return the type float . double value [ ]
         */
        TypeFloat.doubleValue[] value();
    }

    /**
     * <pre>
     * The interface Big integer values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface bigIntegerValues {
        /**
         * <pre>
         * Value type float . big integer value [ ].
         * </pre>
         *
         * @return the type float . big integer value [ ]
         */
        TypeFloat.bigIntegerValue[] value();
    }

    /**
     * <pre>
     * The interface Big decimal values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface bigDecimalValues {
        /**
         * <pre>
         * Value type float . big decimal value [ ].
         * </pre>
         *
         * @return the type float . big decimal value [ ]
         */
        TypeFloat.bigDecimalValue[] value();
    }

    /**
     * <pre>
     * The interface Byte values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface byteValues {
        /**
         * <pre>
         * Value type float . byte value [ ].
         * </pre>
         *
         * @return the type float . byte value [ ]
         */
        TypeFloat.byteValue[] value();
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
         * Value type float . cell value [ ].
         * </pre>
         *
         * @return the type float . cell value [ ]
         */
        TypeFloat.cellValue[] value();
    }
}
