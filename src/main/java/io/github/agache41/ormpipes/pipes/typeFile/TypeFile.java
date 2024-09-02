/*
 *    Copyright 2022-2023  Alexandru Agache
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.agache41.ormpipes.pipes.typeFile;

import io.github.agache41.annotator.annotations.Extends;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingSupplier;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipe.DualPipe;

import java.io.File;
import java.lang.annotation.*;
import java.util.stream.Stream;

import static io.github.agache41.ormpipes.config.Constants.DEFAULT;

/**
 * <pre>
 * The interface Type file.
 * </pre>
 */
@Repeatable(TypeFiles.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Extends(DualPipe.class)
public @interface TypeFile {
    /**
     * <pre>
     * The constant strongType.
     * </pre>
     */
    StrongType strongType = StrongType.of(File.class);

    /**
     * String-&gt;File
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
        Class<? extends AnnotablePipe<New, String, File>> read() default FilePipes.NewFile.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<New, String, File>> write() default FilePipes.NewFile.class;

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
     * The interface New resource.
     * </pre>
     */
    @Repeatable(TypeFiles.NewResources.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface NewResource {

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
        Class<? extends AnnotablePipe<NewResource, String, File>> read() default FilePipes.NewResourceFile.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<NewResource, String, File>> write() default FilePipes.NewResourceFile.class;

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
     * The interface New temporary.
     * </pre>
     */
    @Repeatable(TypeFiles.NewTemporarys.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface NewTemporary {
        /**
         * <pre>
         * Prefix string.
         * </pre>
         *
         * @return the string
         */
        String prefix() default "tmp";

        /**
         * <pre>
         * Suffix string.
         * </pre>
         *
         * @return the string
         */
        String suffix() default ".tmp";

        /**
         * <pre>
         * Resource boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean resource() default false;

        /**
         * <pre>
         * Delete on exit boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean deleteOnExit() default false;

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
        Class<? extends AnnotablePipe<NewTemporary, String, File>> read() default FilePipes.NewTemporaryFile.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<NewTemporary, String, File>> write() default FilePipes.NewTemporaryFile.class;

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
     * The interface Mkdirs.
     * </pre>
     */
    @Repeatable(TypeFiles.mkdirss.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface mkdirs {

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"write"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<mkdirs, File, File>> read() default FilePipes.MkDirsPipe.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<mkdirs, File, File>> write() default FilePipes.MkDirsPipe.class;

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
     * The interface List files.
     * </pre>
     */
    @Repeatable(TypeFiles.listFiless.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface listFiles {

        /**
         * <pre>
         * Recurse boolean.
         * </pre>
         *
         * @return the boolean
         */
        boolean recurse() default true;

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
        Class<? extends AnnotablePipe<TypeFile.listFiles, File, Stream<File>>> read() default FilePipes.ListFilesPipe.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeFile.listFiles, File, ThrowingSupplier<File>>> write() default FilePipes.FolderOutputPipe.class;

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
     * The interface Filter.
     * </pre>
     */
    @Repeatable(TypeFiles.filters.class)
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Extends(DualPipe.class)
    @interface filter {

        /**
         * <pre>
         * Value string.
         * </pre>
         *
         * @return the string
         */
        String value() default "*.*";

        /**
         * <pre>
         * Enabled on string [ ].
         * </pre>
         *
         * @return the string [ ]
         */
        String[] enabledOn() default {"read"};

        /**
         * <pre>
         * Read class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeFile.filter, Stream<File>, Stream<File>>> read() default FilePipes.FilterFilesPipe.class;

        /**
         * <pre>
         * Write class.
         * </pre>
         *
         * @return the class
         */
        Class<? extends AnnotablePipe<TypeFile.filter, ThrowingSupplier<File>, ThrowingSupplier<File>>> write() default FilePipes.IdentityFilterPipe.class;

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
