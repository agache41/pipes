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

package io.github.agache41.ormpipes.pipes.numeric.typeDouble;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type doubles.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeDoubles {
    /**
     * <pre>
     * Value type double [ ].
     * </pre>
     *
     * @return the type double [ ]
     */
    TypeDouble[] value();

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
         * Value type double . new [ ].
         * </pre>
         *
         * @return the type double . new [ ]
         */
        TypeDouble.New[] value();
    }

    /**
     * <pre>
     * The interface Values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface values {
        /**
         * <pre>
         * Value type double . value [ ].
         * </pre>
         *
         * @return the type double . value [ ]
         */
        TypeDouble.value[] value();
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
         * Value type double . short value [ ].
         * </pre>
         *
         * @return the type double . short value [ ]
         */
        TypeDouble.shortValue[] value();
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
         * Value type double . int value [ ].
         * </pre>
         *
         * @return the type double . int value [ ]
         */
        TypeDouble.intValue[] value();
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
         * Value type double . long value [ ].
         * </pre>
         *
         * @return the type double . long value [ ]
         */
        TypeDouble.longValue[] value();
    }

    /**
     * <pre>
     * The interface Float values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface floatValues {
        /**
         * <pre>
         * Value type double . float value [ ].
         * </pre>
         *
         * @return the type double . float value [ ]
         */
        TypeDouble.floatValue[] value();
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
         * Value type double . big integer value [ ].
         * </pre>
         *
         * @return the type double . big integer value [ ]
         */
        TypeDouble.bigIntegerValue[] value();
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
         * Value type double . big decimal value [ ].
         * </pre>
         *
         * @return the type double . big decimal value [ ]
         */
        TypeDouble.bigDecimalValue[] value();
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
         * Value type double . byte value [ ].
         * </pre>
         *
         * @return the type double . byte value [ ]
         */
        TypeDouble.byteValue[] value();
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
         * Value type double . cell value [ ].
         * </pre>
         *
         * @return the type double . cell value [ ]
         */
        TypeDouble.cellValue[] value();
    }
}
