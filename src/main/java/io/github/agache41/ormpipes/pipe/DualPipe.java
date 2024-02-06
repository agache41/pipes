package io.github.agache41.ormpipes.pipe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
public @interface DualPipe {
    String ENABLED_ON = "enabledOn";
    String READ = "read";
    String WRITE = "write";

    String[] enabledOn() default {READ, WRITE};

    Class<? extends AnnotablePipe> read();

    Class<? extends AnnotablePipe> write();

    String view() default DEFAULT;

}
