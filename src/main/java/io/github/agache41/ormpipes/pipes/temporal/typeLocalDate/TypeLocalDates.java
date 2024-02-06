package io.github.agache41.ormpipes.pipes.temporal.typeLocalDate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type local dates.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeLocalDates {
    /**
     * <pre>
     * Value type local date [ ].
     * </pre>
     *
     * @return the type local date [ ]
     */
    TypeLocalDate[] value();

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
         * Value type local date . new [ ].
         * </pre>
         *
         * @return the type local date . new [ ]
         */
        TypeLocalDate.New[] value();
    }

    /**
     * <pre>
     * The interface Nows.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface nows {
        /**
         * <pre>
         * Value type local date . now [ ].
         * </pre>
         *
         * @return the type local date . now [ ]
         */
        TypeLocalDate.now[] value();
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
         * Value type local date . cell value [ ].
         * </pre>
         *
         * @return the type local date . cell value [ ]
         */
        TypeLocalDate.cellValue[] value();
    }
}
