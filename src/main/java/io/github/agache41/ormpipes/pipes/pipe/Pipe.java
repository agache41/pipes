package io.github.agache41.ormpipes.pipes.pipe;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.lang.annotation.*;

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;


@Repeatable(Pipes.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends(DualPipe.class)
public @interface Pipe {
    @Repeatable(Pipes.ofClasses.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface ofClass {
        Class<?> value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<ofClass, ?, ?>> read() default PipePipes.ReadPipe.class;

        Class<? extends AnnotablePipe<ofClass, ?, ?>> write() default PipePipes.WritePipe.class;

        String view() default DEFAULT;
    }
}
