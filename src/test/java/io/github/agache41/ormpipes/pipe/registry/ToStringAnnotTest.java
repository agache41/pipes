package io.github.agache41.ormpipes.pipe.registry;

import io.github.agache41.ormpipes.pipe.AnnotablePipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToStringAnnotTest {
    Class<? extends AnnotablePipe<ToStringAnnotTest, Integer, String>> read() default ToStringPipeTest.class;

    String[] enabledOn() default {"read", "list", "blabla"};

    Class<? extends List> list() default ArrayList.class;
}
