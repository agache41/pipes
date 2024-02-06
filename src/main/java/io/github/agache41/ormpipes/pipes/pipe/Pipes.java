package io.github.agache41.ormpipes.pipes.pipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Pipes.
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pipes {
    /**
     * <pre>
     * Value pipe [ ].
     * </pre>
     *
     * @return the pipe [ ]
     */
    Pipe[] value();

    /**
     * <pre>
     * The interface Of classes.
     * </pre>
     */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface ofClasses {
        /**
         * <pre>
         * Value pipe . of class [ ].
         * </pre>
         *
         * @return the pipe . of class [ ]
         */
        Pipe.ofClass[] value();
    }
}
