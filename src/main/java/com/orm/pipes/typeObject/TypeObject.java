package com.orm.pipes.typeObject;

import com.orm.annotations.Extends;
import com.orm.functional.StrongType;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;

import java.lang.annotation.*;

import static com.orm.annotations.Annotations.DEFAULT;

@Repeatable(TypeObjects.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Extends(DualPipe.class)
public @interface TypeObject {
    StrongType strongType = StrongType.of(Object.class);

    @Repeatable(TypeObjects.toUpperCases.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface toString {
        boolean nullSafe() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<toString, ?, String>> read() default ObjectPipes.ToString.class;

        Class<? extends AnnotablePipe<toString, ?, String>> write() default ObjectPipes.ToString.class;

        String view() default DEFAULT;
    }
}
