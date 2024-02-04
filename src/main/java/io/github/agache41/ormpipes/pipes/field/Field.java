package io.github.agache41.ormpipes.pipes.field;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.ThrowingConsumer;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;
import io.github.agache41.ormpipes.pipes.base.othogonal.OrthogonalFile;

import java.lang.annotation.*;

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;

@Repeatable(Fields.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Field {
    @Repeatable(Fields.ForEachFieldHavings.class)
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends({DualPipe.class, OrthogonalFile.class})
    @interface forEachAnnotatedWith {
        Class<? extends Annotation> value();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<forEachAnnotatedWith, ?, ?>> read() default FieldPipes.ReadForEachAnnotatedWith.class;

        Class<? extends AnnotablePipe<forEachAnnotatedWith, ?, ThrowingConsumer<?>>> write() default FieldPipes.WriteForEachAnnotatedWith.class;

        String view() default DEFAULT;
    }
}