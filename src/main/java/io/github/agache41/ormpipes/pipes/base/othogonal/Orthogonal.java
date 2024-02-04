package io.github.agache41.ormpipes.pipes.base.othogonal;

import static io.github.agache41.annotator.accessor.Accessor.NO_POSITION;
public @interface Orthogonal {
    String name() default "";

    int position() default NO_POSITION;

    boolean required() default false;
}
