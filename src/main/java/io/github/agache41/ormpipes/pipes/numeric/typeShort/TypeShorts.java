package io.github.agache41.ormpipes.pipes.numeric.typeShort;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type shorts.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeShorts {
    /**
     * <pre>
     * Value type short [ ].
     * </pre>
     *
     * @return the type short [ ]
     */
    TypeShort[] value();

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
         * Value type short . new [ ].
         * </pre>
         *
         * @return the type short . new [ ]
         */
        TypeShort.New[] value();
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
         * Value type short . value [ ].
         * </pre>
         *
         * @return the type short . value [ ]
         */
        TypeShort.value[] value();
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
         * Value type short . int value [ ].
         * </pre>
         *
         * @return the type short . int value [ ]
         */
        TypeShort.intValue[] value();
    }

    /**
     * <pre>
     * The interface Unsigned int values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface unsignedIntValues {
        /**
         * <pre>
         * Value type short . unsigned int value [ ].
         * </pre>
         *
         * @return the type short . unsigned int value [ ]
         */
        TypeShort.unsignedIntValue[] value();
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
         * Value type short . long value [ ].
         * </pre>
         *
         * @return the type short . long value [ ]
         */
        TypeShort.longValue[] value();
    }

    /**
     * <pre>
     * The interface Unsigned long values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface unsignedLongValues {
        /**
         * <pre>
         * Value type short . unsigned long value [ ].
         * </pre>
         *
         * @return the type short . unsigned long value [ ]
         */
        TypeShort.unsignedLongValue[] value();
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
         * Value type short . double value [ ].
         * </pre>
         *
         * @return the type short . double value [ ]
         */
        TypeShort.doubleValue[] value();
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
         * Value type short . float value [ ].
         * </pre>
         *
         * @return the type short . float value [ ]
         */
        TypeShort.floatValue[] value();
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
         * Value type short . big integer value [ ].
         * </pre>
         *
         * @return the type short . big integer value [ ]
         */
        TypeShort.bigIntegerValue[] value();
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
         * Value type short . big decimal value [ ].
         * </pre>
         *
         * @return the type short . big decimal value [ ]
         */
        TypeShort.bigDecimalValue[] value();
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
         * Value type short . byte value [ ].
         * </pre>
         *
         * @return the type short . byte value [ ]
         */
        TypeShort.byteValue[] value();
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
         * Value type short . cell value [ ].
         * </pre>
         *
         * @return the type short . cell value [ ]
         */
        TypeShort.cellValue[] value();
    }
}
