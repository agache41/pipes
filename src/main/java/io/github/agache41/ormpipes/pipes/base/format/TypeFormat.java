package io.github.agache41.ormpipes.pipes.base.format;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TypeFormat {
    String format() default "";

    String languageTag() default "";

    boolean simple() default false;

    boolean nullSafe() default true;

    boolean blankSafe() default true;

    boolean noException() default true;
}
