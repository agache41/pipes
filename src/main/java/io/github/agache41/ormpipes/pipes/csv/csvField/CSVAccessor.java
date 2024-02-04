package io.github.agache41.ormpipes.pipes.csv.csvField;


import io.github.agache41.ormpipes.config.Annotations;
import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.Orthogonal;

import java.lang.annotation.*;

import static io.github.agache41.annotator.accessor.Accessor.NO_POSITION;


@Repeatable(CSVAccessors.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends({DualPipe.class, Orthogonal.class})
public @interface CSVAccessor {
    String name() default "";

    int position() default NO_POSITION;

    boolean required() default false;

    String[] enabledOn() default {"read", "write"};

    Class<? extends AnnotablePipe<CSVAccessor, ?, ThrowingConsumer<?>>> read() default Setter.class;

    Class<? extends AnnotablePipe<CSVAccessor, ?, ?>> write() default Getter.class;

    String view() default Annotations.DEFAULT;
}
