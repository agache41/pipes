package io.github.agache41.ormpipes.pipes.temporal.typeLocalTime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Type local times.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface TypeLocalTimes {
    /**
     * <pre>
     * Value type local time [ ].
     * </pre>
     *
     * @return the type local time [ ]
     */
    TypeLocalTime[] value();

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
         * Value type local time . new [ ].
         * </pre>
         *
         * @return the type local time . new [ ]
         */
        TypeLocalTime.New[] value();
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
         * Value type local time . now [ ].
         * </pre>
         *
         * @return the type local time . now [ ]
         */
        TypeLocalTime.now[] value();
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
         * Value type local time . cell value [ ].
         * </pre>
         *
         * @return the type local time . cell value [ ]
         */
        TypeLocalTime.cellValue[] value();
    }
}
