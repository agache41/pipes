package io.github.agache41.ormpipes.pipes.iostream;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.*;

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;


@Repeatable(IOStreams.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends(DualPipe.class)
public @interface IOStream {
    @Repeatable(IOStreams.Files.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface FileBased {
        boolean append() default false;

        int bufferSizeKB() default 0;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<FileBased, File, InputStream>> read() default IOStreamPipes.FileInputStreamPipe.class;

        Class<? extends AnnotablePipe<FileBased, File, OutputStream>> write() default IOStreamPipes.FileOutputStreamPipe.class;

        String view() default DEFAULT;
    }
}
