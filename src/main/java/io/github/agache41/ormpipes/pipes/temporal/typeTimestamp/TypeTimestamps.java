package io.github.agache41.ormpipes.pipes.temporal.typeTimestamp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type timestamps.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeTimestamps {
    /**
     * <pre>
     * Value type timestamp [ ].
     * </pre>
     *
     * @return the type timestamp [ ]
     */
    TypeTimestamp[] value();

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
         * Value type timestamp . new [ ].
         * </pre>
         *
         * @return the type timestamp . new [ ]
         */
        TypeTimestamp.New[] value();
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
         * Value type timestamp . now [ ].
         * </pre>
         *
         * @return the type timestamp . now [ ]
         */
        TypeTimestamp.now[] value();
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
         * Value type timestamp . cell value [ ].
         * </pre>
         *
         * @return the type timestamp . cell value [ ]
         */
        TypeTimestamp.cellValue[] value();
    }
}
