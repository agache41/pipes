package io.github.agache41.ormpipes.pipes.numeric.typeLong;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type longs.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeLongs {
    /**
     * <pre>
     * Value type long [ ].
     * </pre>
     *
     * @return the type long [ ]
     */
    TypeLong[] value();

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
         * Value type long . new [ ].
         * </pre>
         *
         * @return the type long . new [ ]
         */
        TypeLong.New[] value();
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
         * Value type long . value [ ].
         * </pre>
         *
         * @return the type long . value [ ]
         */
        TypeLong.value[] value();
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
         * Value type long . short value [ ].
         * </pre>
         *
         * @return the type long . short value [ ]
         */
        TypeLong.shortValue[] value();
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
         * Value type long . int value [ ].
         * </pre>
         *
         * @return the type long . int value [ ]
         */
        TypeLong.intValue[] value();
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
         * Value type long . double value [ ].
         * </pre>
         *
         * @return the type long . double value [ ]
         */
        TypeLong.doubleValue[] value();
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
         * Value type long . float value [ ].
         * </pre>
         *
         * @return the type long . float value [ ]
         */
        TypeLong.floatValue[] value();
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
         * Value type long . big integer value [ ].
         * </pre>
         *
         * @return the type long . big integer value [ ]
         */
        TypeLong.bigIntegerValue[] value();
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
         * Value type long . big decimal value [ ].
         * </pre>
         *
         * @return the type long . big decimal value [ ]
         */
        TypeLong.bigDecimalValue[] value();
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
         * Value type long . byte value [ ].
         * </pre>
         *
         * @return the type long . byte value [ ]
         */
        TypeLong.byteValue[] value();
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
         * Value type long . cell value [ ].
         * </pre>
         *
         * @return the type long . cell value [ ]
         */
        TypeLong.cellValue[] value();
    }
}
