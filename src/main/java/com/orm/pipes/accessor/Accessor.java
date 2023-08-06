package com.orm.pipes.accessor;


import com.orm.annotations.Annotations;
import com.orm.annotations.Extends;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;

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
