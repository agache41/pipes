package io.github.agache41.ormpipes.pipes.typeFile;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingSupplier;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.io.File;
import java.lang.annotation.*;
import java.util.stream.Stream;

import static io.github.agache41.ormpipes.config.Annotations.DEFAULT;

@Repeatable(TypeFiles.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends(DualPipe.class)
public @interface TypeFile {
    StrongType strongType = StrongType.of(File.class);

    /**
     * String->File
     * <p>
     * Creates a File based on the input String as Filename.
     * <p>
     * Input: String
     * <p>
     * Output: File
     */
    @Repeatable(TypeFiles.News.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface New {

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<New, String, File>> read() default FilePipes.NewFile.class;

        Class<? extends AnnotablePipe<New, String, File>> write() default FilePipes.NewFile.class;

        String view() default DEFAULT;

    }

    @Repeatable(TypeFiles.NewResources.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface NewResource {

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<NewResource, String, File>> read() default FilePipes.NewResourceFile.class;

        Class<? extends AnnotablePipe<NewResource, String, File>> write() default FilePipes.NewResourceFile.class;

        String view() default DEFAULT;

    }

    @Repeatable(TypeFiles.NewTemporarys.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface NewTemporary {
        String prefix() default "tmp";

        String suffix() default ".tmp";

        boolean resource() default false;

        boolean deleteOnExit() default false;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<NewTemporary, String, File>> read() default FilePipes.NewTemporaryFile.class;

        Class<? extends AnnotablePipe<NewTemporary, String, File>> write() default FilePipes.NewTemporaryFile.class;

        String view() default DEFAULT;

    }

    @Repeatable(TypeFiles.mkdirss.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface mkdirs {

        String[] enabledOn() default {"write"};

        Class<? extends AnnotablePipe<mkdirs, File, File>> read() default FilePipes.MkDirsPipe.class;

        Class<? extends AnnotablePipe<mkdirs, File, File>> write() default FilePipes.MkDirsPipe.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeFiles.listFiless.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface listFiles {

        boolean recurse() default true;

        String[] enabledOn() default {"read", "write"};

        Class<? extends AnnotablePipe<TypeFile.listFiles, File, Stream<File>>> read() default FilePipes.ListFilesPipe.class;

        Class<? extends AnnotablePipe<TypeFile.listFiles, File, ThrowingSupplier<File>>> write() default FilePipes.FolderOutputPipe.class;

        String view() default DEFAULT;
    }

    @Repeatable(TypeFiles.filters.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface filter {

        String value() default "*.*";

        String[] enabledOn() default {"read"};

        Class<? extends AnnotablePipe<TypeFile.filter, Stream<File>, Stream<File>>> read() default FilePipes.FilterFilesPipe.class;

        Class<? extends AnnotablePipe<TypeFile.filter, ThrowingSupplier<File>, ThrowingSupplier<File>>> write() default FilePipes.IdentityFilterPipe.class;

        String view() default DEFAULT;
    }
}
