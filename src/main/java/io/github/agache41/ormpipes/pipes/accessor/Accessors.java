package io.github.agache41.ormpipes.pipes.accessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * The interface Accessors.
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Accessors {
    /**
     * <pre>
     * Value accessor [ ].
     * </pre>
     *
     * @return the accessor [ ]
     */
    Accessor[] value();
}
