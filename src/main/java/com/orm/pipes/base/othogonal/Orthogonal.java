package com.orm.pipes.base.othogonal;

import static com.orm.reflection.accessor.Accessor.NO_POSITION;

public @interface Orthogonal {
    String name() default "";

    int position() default NO_POSITION;

    boolean required() default false;
}
