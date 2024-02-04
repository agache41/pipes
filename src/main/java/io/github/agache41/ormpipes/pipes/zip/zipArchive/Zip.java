package io.github.agache41.ormpipes.pipes.zip.zipArchive;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;


@Repeatable(Zips.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends(DualPipe.class)
public @interface Zip {
    @Repeatable(Zips.Archives.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Archive {

        String extension = ".zip";

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Archive, InputStream, ZipInputStream>> read() default ZipArchivePipes.ArchiveInputStreamPipe.class;

        Class<? extends AnnotablePipe<Archive, OutputStream, ZipOutputStream>> write() default ZipArchivePipes.ArchiveOutputStreamPipe.class;

        String view() default DEFAULT;
    }

    @Repeatable(Zips.Files.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface Entry {

        String fileName();

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<Entry, ZipInputStream, InputStream>> read() default ZipArchivePipes.ZipEntryInputStreamPipe.class;

        Class<? extends AnnotablePipe<Entry, ZipOutputStream, OutputStream>> write() default ZipArchivePipes.ZipEntryOutputStreamPipe.class;

        String view() default DEFAULT;
    }
}
