package io.github.agache41.ormpipes.pipes.zip.zipArchive;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;


/**
 * <pre>
 * The interface Zip.
 * </pre>
 */
@Repeatable(Zips.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends(DualPipe.class)
public @interface Zip {
    /**
     * <pre>
     * The interface Archive.
     * </pre>
     */
    @Repeatable(Zips.Archives.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Archive {

        /**
         * <pre>
         * The constant extension.
         * </pre>
         */
        String extension = ".zip";

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
        Class<? extends AnnotablePipe<Archive, InputStream, ZipInputStream>> read() default ZipArchivePipes.ArchiveInputStreamPipe.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Archive, OutputStream, ZipOutputStream>> write() default ZipArchivePipes.ArchiveOutputStreamPipe.class;

        /**
         * <pre>
         * View string.
         * </pre>
         *
         * @return the string
         */
        String view() default DEFAULT;
    }

    /**
     * <pre>
     * The interface Entry.
     * </pre>
     */
    @Repeatable(Zips.Files.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Entry {

        /**
         * <pre>
         * File name string.
         * </pre>
         *
         * @return the string
         */
        String fileName();

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
        Class<? extends AnnotablePipe<Entry, ZipInputStream, InputStream>> read() default ZipArchivePipes.ZipEntryInputStreamPipe.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<Entry, ZipOutputStream, OutputStream>> write() default ZipArchivePipes.ZipEntryOutputStreamPipe.class;

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
