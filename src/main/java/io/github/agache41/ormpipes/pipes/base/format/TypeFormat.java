package io.github.agache41.ormpipes.pipes.base.format;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * The interface Type format.
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeFormat {
    /**
     * <pre>
     * Format string.
     * </pre>
     *
     * @return the string
     */
    String format() default "";

    /**
     * <pre>
     * Language tag string.
     * </pre>
     *
     * @return the string
     */
    String languageTag() default "";

    /**
     * <pre>
     * Simple boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean simple() default false;

    /**
     * <pre>
     * Null safe boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean nullSafe() default true;

    /**
     * <pre>
     * Blank safe boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean blankSafe() default true;

    /**
     * <pre>
     * No exception boolean.
     * </pre>
     *
     * @return the boolean
     */
    boolean noException() default true;
}
