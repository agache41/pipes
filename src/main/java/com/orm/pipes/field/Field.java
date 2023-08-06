package com.orm.pipes.field;

import com.orm.annotations.Extends;
import com.orm.functional.ThrowingConsumer;
import com.orm.pipe.AnnotablePipe;
import com.orm.pipe.DualPipe;
import com.orm.pipes.base.othogonal.OrthogonalFile;

import java.lang.annotation.*;

import static com.orm.annotations.Annotations.DEFAULT;

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