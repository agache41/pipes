package com.orm.pipes.pipe;

import com.orm.annotations.Extends;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;

import java.lang.annotation.*;

import static com.orm.annotations.Annotations.DEFAULT;


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
