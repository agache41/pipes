package io.github.agache41.ormpipes.pipes.base.othogonal;

import static io.github.agache41.annotator.accessor.Accessor.NO_POSITION;

/**
 * <pre>
 * The interface Orthogonal.
 * </pre>
 */
public @interface Orthogonal {
    /**
     * <pre>
     * Name string.
     * </pre>
     *
     * @return the string
     */
    String name() default "";

    /**
     * <pre>
     * Position int.
     * </pre>
     *
     * @return the int
     */
    int position() default NO_POSITION;

    /**
     * <pre>
     * Required boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean required() default false;
}
