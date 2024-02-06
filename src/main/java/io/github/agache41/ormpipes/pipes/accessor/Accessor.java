package io.github.agache41.ormpipes.pipes.accessor;


import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.lang.annotation.*;

/**
 * <pre>
 * The interface Accessor.
 * </pre>
 */
@Repeatable(Accessors.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends({DualPipe.class})
public @interface Accessor {

    /**
     * <pre>
     * Enabled on string [ ].
     * </pre>
     *
     * @return the string [ ]
     */
    String[] enabledOn() default {"read", "write"};

    /**
     * <pre>
     * Read class.
     * </pre>
     *
     * @return the class
     */
    Class<? extends AnnotablePipe<Accessor, ?, ThrowingConsumer<?>>> read() default Setter.class;

    /**
     * <pre>
     * Write class.
     * </pre>
     *
     * @return the class
     */
    Class<? extends AnnotablePipe<Accessor, ?, ?>> write() default Getter.class;

    /**
     * <pre>
     * View string.
     * </pre>
     *
     * @return the string
     */
    String view() default Constants.DEFAULT;
}
