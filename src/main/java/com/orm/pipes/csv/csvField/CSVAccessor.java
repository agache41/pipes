package com.orm.pipes.csv.csvField;


import com.orm.annotations.Annotations;
import com.orm.annotations.Extends;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import com.orm.pipes.base.othogonal.Orthogonal;

import java.lang.annotation.*;

import static com.orm.reflection.accessor.Accessor.NO_POSITION;

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
