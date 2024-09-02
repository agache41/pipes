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


import io.github.agache41.annotator.annotations.Recursive;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.functional.ThrowingSupplier;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * <pre>
 * The type File pipes.
 * </pre>
 */
public class FilePipes {

    private static final Logger logger = Logger.getLogger(FilePipes.class);
    /**
     * <pre>
     * The constant resourceDirectory.
     * </pre>
     */
    public static final File resourceDirectory = getResourceDirectory();
    /**
     * <pre>
     * The constant temporaryDirectory.
     * </pre>
     */
    public static final File temporaryDirectory = getSystemTemporaryDirectory();

    private static final File getResourceDirectory() {
        URL base = ClassLoader.getSystemResource("");
        if (null == base) {
            throw new IllegalStateException(" System Resource not available: ClassLoader.getSystemResource() returned null");
        }
        final File resourceDirectory = new File(base.getFile());
        if (!resourceDirectory.exists()) {
            throw new IllegalStateException(" System Resource Directory does not exists:" + resourceDirectory.getAbsolutePath());
        }
        if (!resourceDirectory.isDirectory()) {
            throw new IllegalStateException(" System Resource Directory exists but is a file:" + resourceDirectory.getAbsolutePath());
        }
        logger.debugf(" System Resource Directory set to %s", resourceDirectory.getAbsolutePath());
        return resourceDirectory;
    }

    private static final File getSystemTemporaryDirectory() {
        String javaTmpDir = System.getProperty("java.io.tmpdir");
        if (null == javaTmpDir || javaTmpDir.isEmpty()) {
            throw new IllegalStateException(" System Temporary Directory not available: java.io.tmpdir not set");
        }
        final File temporaryDirectory = new File(javaTmpDir);
        if (!temporaryDirectory.exists()) {
            throw new IllegalStateException(" System Temporary Directory does not exists:" + temporaryDirectory.getAbsolutePath());
        }
        if (!temporaryDirectory.isDirectory()) {
            throw new IllegalStateException(" System Temporary Directory exists but is a file:" + temporaryDirectory.getAbsolutePath());
        }
        logger.debugf("System Temporary Directory set to %s", resourceDirectory.getAbsolutePath());
        return temporaryDirectory;
    }

    /**
     * <pre>
     * The type New file.
     * </pre>
     */
    public static class NewFile implements AnnotablePipe<TypeFile.New, String, File> {
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeFile.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFile.New cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, File> function() {
            return File::new;
        }
    }

    /**
     * <pre>
     * The type New resource file.
     * </pre>
     */
    public static class NewResourceFile implements AnnotablePipe<TypeFile.NewResource, String, File> {
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeFile.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFile.NewResource cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, File> function() {
            return filename -> new File(FilePipes.resourceDirectory, filename);
        }
    }

    /**
     * <pre>
     * The type New temporary file.
     * </pre>
     */
    public static class NewTemporaryFile implements AnnotablePipe<TypeFile.NewTemporary, String, File> {

        /**
         * <pre>
         * The Prefix.
         * </pre>
         */
        String prefix;
        /**
         * <pre>
         * The Suffix.
         * </pre>
         */
        String suffix;
        /**
         * <pre>
         * The Resource.
         * </pre>
         */
        boolean resource;
        /**
         * <pre>
         * The Delete on exit.
         * </pre>
         */
        boolean deleteOnExit;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeFile.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFile.NewTemporary cfg) {
            this.prefix = cfg.prefix();
            this.suffix = cfg.suffix();
            this.resource = cfg.resource();
            this.deleteOnExit = cfg.deleteOnExit();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<String, File> function() {
            return filename -> {
                File directory;
                if (this.resource) {
                    directory = new File(resourceDirectory, filename);
                } else {
                    directory = new File(temporaryDirectory, filename);
                }
                directory.mkdirs();
                File result = File.createTempFile(this.prefix, this.suffix, directory);
                if (this.deleteOnExit) {
                    result.deleteOnExit();
                }
                return result;
            };
        }
    }

    /**
     * <pre>
     * The type Mk dirs pipe.
     * </pre>
     */
    public static class MkDirsPipe implements AnnotablePipe<TypeFile.mkdirs, File, File> {
        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFile.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getOutputType() {
            return TypeFile.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFile.mkdirs cfg) {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<File, File> function() {
            return file -> {
                File parent = file.getParentFile();
                if (parent == null) {
                    throw new FileNotFoundException("Parent directory not found:" + file.getAbsolutePath());
                }
                if (!parent.exists()) {
                    parent.mkdirs();
                    if (!parent.exists()) {
                        throw new FileNotFoundException("Parent directory could not be created:" + file.getAbsolutePath());
                    }
                } else if (parent.isFile()) {
                    throw new FileAlreadyExistsException("Parent already exists and is a file:" + file.getAbsolutePath());
                } else if (!parent.canWrite()) {
                    throw new SecurityException("Insufficient Rights in parent Directory:" + file.getAbsolutePath());
                }
                return file;
            };
        }
    }


    /**
     * <pre>
     * The type List files pipe.
     * </pre>
     */
    public static class ListFilesPipe implements AnnotablePipe<TypeFile.listFiles, File, Stream<File>> {
        private boolean recurse;

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFile.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFile.listFiles cfg) {
            this.recurse = cfg.recurse();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<File, Stream<File>> function() {
            return this::listFiles;
        }

        @Recursive
        private Stream<File> listFiles(File file) {
            if (file.isFile()) {
                return Stream.of(file);
            }
            if (this.recurse) {
                return Arrays.stream(file.listFiles())
                             .flatMap(this::listFiles);
            } else {
                return Arrays.stream(file.listFiles())
                             .filter(File::isFile);
            }
        }
    }

    /**
     * <pre>
     * The type Filter files pipe.
     * </pre>
     */
    public static class FilterFilesPipe implements AnnotablePipe<TypeFile.filter, Stream<File>, Stream<File>> {
        private ThrowingFunction<Stream<File>, Stream<File>> function;

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFile.filter cfg) {
            if ("*.*".equals(cfg.value())) {
                this.function = ThrowingFunction.identity();
                return;
            }
            final Pattern pattern = Pattern.compile(cfg.value()
                                                       .replace(".",
                                                                "\\.")
                                                       .replace("*",
                                                                ".*")
                                                       .replace("/",
                                                                "\\/"));
            this.function = stream -> stream.filter(file -> pattern.matcher(file.getAbsolutePath())
                                                                   .matches());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<Stream<File>, Stream<File>> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type Identity filter pipe.
     * </pre>
     */
    public static class IdentityFilterPipe implements AnnotablePipe<TypeFile.filter, ThrowingSupplier<File>, ThrowingSupplier<File>> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFile.filter cfg) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<ThrowingSupplier<File>, ThrowingSupplier<File>> function() {
            return ThrowingFunction.identity();
        }
    }


    /**
     * <pre>
     * The type Folder output pipe.
     * </pre>
     */
    public class FolderOutputPipe implements AnnotablePipe<TypeFile.listFiles, File, ThrowingSupplier<File>> {

        /**
         * {@inheritDoc}
         */
        @Override
        public StrongType getInputType() {
            return TypeFile.strongType;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(TypeFile.listFiles cfg) {

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<File, ThrowingSupplier<File>> function() {
            return file -> new ThrowingSupplier<File>() {
                int index = 1;

                /**
                 * {@inheritDoc}
                 */
                @Override
                public File get() throws Throwable {
                    return new File(file, file.getName() + "." + this.index++);
                }
            };
        }
    }

}
