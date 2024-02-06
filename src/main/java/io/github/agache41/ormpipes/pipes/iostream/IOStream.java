package io.github.agache41.ormpipes.pipes.iostream;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.*;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;


/**
 * <pre>
 * The interface Io stream.
 * </pre>
 */
@Repeatable(IOStreams.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends(DualPipe.class)
public @interface IOStream {
    /**
     * <pre>
     * The interface File based.
     * </pre>
     */
    @Repeatable(IOStreams.Files.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface FileBased {
        /**
         * <pre>
         * Append boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean append() default false;

        /**
         * <pre>
         * Buffer size kb int.
         * </pre>
         *
         * @return the int
         */
        int bufferSizeKB() default 0;

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read", "write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<FileBased, File, InputStream>> read() default IOStreamPipes.FileInputStreamPipe.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<FileBased, File, OutputStream>> write() default IOStreamPipes.FileOutputStreamPipe.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;
    }
}
