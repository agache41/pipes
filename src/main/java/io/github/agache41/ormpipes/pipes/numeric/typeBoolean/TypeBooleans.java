package io.github.agache41.ormpipes.pipes.numeric.typeBoolean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type booleans.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeBooleans {
    /**
     * <pre>
     * Value type boolean [ ].
     * </pre>
     *
     * @return the type boolean [ ]
     */
    TypeBoolean[] value();

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
         * Value type boolean . new [ ].
         * </pre>
         *
         * @return the type boolean . new [ ]
         */
        TypeBoolean.New[] value();
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
         * Value type boolean . value [ ].
         * </pre>
         *
         * @return the type boolean . value [ ]
         */
        TypeBoolean.value[] value();
    }

    /**
     * <pre>
     * The interface Negates.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface negates {
        /**
         * <pre>
         * Value type boolean . negate [ ].
         * </pre>
         *
         * @return the type boolean . negate [ ]
         */
        TypeBoolean.negate[] value();
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
         * Value type boolean . cell value [ ].
         * </pre>
         *
         * @return the type boolean . cell value [ ]
         */
        TypeBoolean.cellValue[] value();
    }
}
