package io.github.agache41.ormpipes.pipes.accessor;


import io.github.agache41.ormpipes.config.Annotations;
import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.lang.annotation.*;

@Repeatable(Accessors.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends({DualPipe.class})
public @interface Accessor {

    String[] enabledOn() default {"read", "write"};

    Class<? extends AnnotablePipe<Accessor, ?, ThrowingConsumer<?>>> read() default Setter.class;

    Class<? extends AnnotablePipe<Accessor, ?, ?>> write() default Getter.class;

    String view() default Annotations.DEFAULT;
}
