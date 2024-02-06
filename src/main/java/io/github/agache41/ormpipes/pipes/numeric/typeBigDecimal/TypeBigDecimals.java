package io.github.agache41.ormpipes.pipes.numeric.typeBigDecimal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type big decimals.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeBigDecimals {
    /**
     * <pre>
     * Value type big decimal [ ].
     * </pre>
     *
     * @return the type big decimal [ ]
     */
    TypeBigDecimal[] value();

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
         * Value type big decimal . new [ ].
         * </pre>
         *
         * @return the type big decimal . new [ ]
         */
        TypeBigDecimal.New[] value();
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
         * Value type big decimal . value [ ].
         * </pre>
         *
         * @return the type big decimal . value [ ]
         */
        TypeBigDecimal.value[] value();
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
         * Value type big decimal . short value [ ].
         * </pre>
         *
         * @return the type big decimal . short value [ ]
         */
        TypeBigDecimal.shortValue[] value();
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
         * Value type big decimal . int value [ ].
         * </pre>
         *
         * @return the type big decimal . int value [ ]
         */
        TypeBigDecimal.intValue[] value();
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
         * Value type big decimal . long value [ ].
         * </pre>
         *
         * @return the type big decimal . long value [ ]
         */
        TypeBigDecimal.longValue[] value();
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
         * Value type big decimal . double value [ ].
         * </pre>
         *
         * @return the type big decimal . double value [ ]
         */
        TypeBigDecimal.doubleValue[] value();
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
         * Value type big decimal . float value [ ].
         * </pre>
         *
         * @return the type big decimal . float value [ ]
         */
        TypeBigDecimal.floatValue[] value();
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
         * Value type big decimal . big integer value [ ].
         * </pre>
         *
         * @return the type big decimal . big integer value [ ]
         */
        TypeBigDecimal.bigIntegerValue[] value();
    }

    /**
     * <pre>
     * The interface Big integer exact values.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface bigIntegerExactValues {
        /**
         * <pre>
         * Value type big decimal . big integer exact value [ ].
         * </pre>
         *
         * @return the type big decimal . big integer exact value [ ]
         */
        TypeBigDecimal.bigIntegerExactValue[] value();
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
         * Value type big decimal . byte value [ ].
         * </pre>
         *
         * @return the type big decimal . byte value [ ]
         */
        TypeBigDecimal.byteValue[] value();
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
         * Value type big decimal . cell value [ ].
         * </pre>
         *
         * @return the type big decimal . cell value [ ]
         */
        TypeBigDecimal.cellValue[] value();
    }
}
