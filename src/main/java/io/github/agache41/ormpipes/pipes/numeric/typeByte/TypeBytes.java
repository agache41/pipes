package io.github.agache41.ormpipes.pipes.numeric.typeByte;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type bytes.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeBytes {
    /**
     * <pre>
     * Value type byte [ ].
     * </pre>
     *
     * @return the type byte [ ]
     */
    TypeByte[] value();

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
         * Value type byte . value [ ].
         * </pre>
         *
         * @return the type byte . value [ ]
         */
        TypeByte.value[] value();
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
         * Value type byte . short value [ ].
         * </pre>
         *
         * @return the type byte . short value [ ]
         */
        TypeByte.shortValue[] value();
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
         * Value type byte . int value [ ].
         * </pre>
         *
         * @return the type byte . int value [ ]
         */
        TypeByte.intValue[] value();
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
         * Value type byte . long value [ ].
         * </pre>
         *
         * @return the type byte . long value [ ]
         */
        TypeByte.longValue[] value();
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
         * Value type byte . double value [ ].
         * </pre>
         *
         * @return the type byte . double value [ ]
         */
        TypeByte.doubleValue[] value();
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
         * Value type byte . float value [ ].
         * </pre>
         *
         * @return the type byte . float value [ ]
         */
        TypeByte.floatValue[] value();
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
         * Value type byte . big integer value [ ].
         * </pre>
         *
         * @return the type byte . big integer value [ ]
         */
        TypeByte.bigIntegerValue[] value();
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
         * Value type byte . big decimal value [ ].
         * </pre>
         *
         * @return the type byte . big decimal value [ ]
         */
        TypeByte.bigDecimalValue[] value();
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
         * Value type byte . cell value [ ].
         * </pre>
         *
         * @return the type byte . cell value [ ]
         */
        TypeByte.cellValue[] value();
    }
}
