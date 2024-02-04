package io.github.agache41.ormpipes.pipes.typeFile;



import io.github.agache41.annotator.annotations.Recursive;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.functional.ThrowingSupplier;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeString.TypeString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FilePipes {

    private static final Logger logger = LogManager.getLogger(FilePipes.class);
    public static final File resourceDirectory = getResourceDirectory();
    public static final File temporaryDirectory = getSystemTemporaryDirectory();

    private static final File getResourceDirectory() {
        URL base = ClassLoader.getSystemResource("");
        if (null == base)
            throw new IllegalStateException(" System Resource not available: ClassLoader.getSystemResource() returned null");
        final File resourceDirectory = new File(base.getFile());
        if (!resourceDirectory.exists())
            throw new IllegalStateException(" System Resource Directory does not exists:" + resourceDirectory.getAbsolutePath());
        if (!resourceDirectory.isDirectory())
            throw new IllegalStateException(" System Resource Directory exists but is a file:" + resourceDirectory.getAbsolutePath());
        logger.debug(" System Resource Directory set to {}", resourceDirectory.getAbsolutePath());
        return resourceDirectory;
    }

    private static final File getSystemTemporaryDirectory() {
        String javaTmpDir = System.getProperty("java.io.tmpdir");
        if (null == javaTmpDir || javaTmpDir.isEmpty())
            throw new IllegalStateException(" System Temporary Directory not available: java.io.tmpdir not set");
        final File temporaryDirectory = new File(javaTmpDir);
        if (!temporaryDirectory.exists())
            throw new IllegalStateException(" System Temporary Directory does not exists:" + temporaryDirectory.getAbsolutePath());
        if (!temporaryDirectory.isDirectory())
            throw new IllegalStateException(" System Temporary Directory exists but is a file:" + temporaryDirectory.getAbsolutePath());
        logger.debug("System Temporary Directory set to {}", resourceDirectory.getAbsolutePath());
        return temporaryDirectory;
    }

    public static class NewFile implements AnnotablePipe<TypeFile.New, String, File> {
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFile.strongType;
        }

        @Override
        public void configure(TypeFile.New cfg) {
        }

        @Override
        public ThrowingFunction<String, File> function() {
            return File::new;
        }
    }

    public static class NewResourceFile implements AnnotablePipe<TypeFile.NewResource, String, File> {
        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFile.strongType;
        }

        @Override
        public void configure(TypeFile.NewResource cfg) {
        }

        @Override
        public ThrowingFunction<String, File> function() {
            return filename -> new File(FilePipes.resourceDirectory, filename);
        }
    }

    public static class NewTemporaryFile implements AnnotablePipe<TypeFile.NewTemporary, String, File> {

        String prefix;
        String suffix;
        boolean resource;
        boolean deleteOnExit;

        @Override
        public StrongType getInputType() {
            return TypeString.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFile.strongType;
        }

        @Override
        public void configure(TypeFile.NewTemporary cfg) {
            this.prefix = cfg.prefix();
            this.suffix = cfg.suffix();
            this.resource = cfg.resource();
            this.deleteOnExit = cfg.deleteOnExit();
        }

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
                if (this.deleteOnExit)
                    result.deleteOnExit();
                return result;
            };
        }
    }

    public static class MkDirsPipe implements AnnotablePipe<TypeFile.mkdirs, File, File> {
        @Override
        public StrongType getInputType() {
            return TypeFile.strongType;
        }

        @Override
        public StrongType getOutputType() {
            return TypeFile.strongType;
        }

        @Override
        public void configure(TypeFile.mkdirs cfg) {

        }

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


    public static class ListFilesPipe implements AnnotablePipe<TypeFile.listFiles, File, Stream<File>> {
        private boolean recurse;

        @Override
        public StrongType getInputType() {
            return TypeFile.strongType;
        }

        @Override
        public void configure(TypeFile.listFiles cfg) {
            this.recurse = cfg.recurse();
        }

        @Override
        public ThrowingFunction<File, Stream<File>> function() {
            return this::listFiles;
        }

        @Recursive
        private Stream<File> listFiles(File file) {
            if (file.isFile())
                return Stream.of(file);
            if (this.recurse)
                return Arrays.stream(file.listFiles())
                             .flatMap(this::listFiles);
            else
                return Arrays.stream(file.listFiles())
                             .filter(File::isFile);
        }
    }

    public static class FilterFilesPipe implements AnnotablePipe<TypeFile.filter, Stream<File>, Stream<File>> {
        private ThrowingFunction<Stream<File>, Stream<File>> function;

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

        @Override
        public ThrowingFunction<Stream<File>, Stream<File>> function() {
            return this.function;
        }
    }

    public static class IdentityFilterPipe implements AnnotablePipe<TypeFile.filter, ThrowingSupplier<File>, ThrowingSupplier<File>> {

        @Override
        public void configure(TypeFile.filter cfg) {
        }

        @Override
        public ThrowingFunction<ThrowingSupplier<File>, ThrowingSupplier<File>> function() {
            return ThrowingFunction.identity();
        }
    }


    public class FolderOutputPipe implements AnnotablePipe<TypeFile.listFiles, File, ThrowingSupplier<File>> {

        @Override
        public StrongType getInputType() {
            return TypeFile.strongType;
        }

        @Override
        public void configure(TypeFile.listFiles cfg) {

        }

        @Override
        public ThrowingFunction<File, ThrowingSupplier<File>> function() {
            return file -> new ThrowingSupplier<File>() {
                int index = 1;

                @Override
                public File get() throws Throwable {
                    return new File(file, file.getName() + "." + this.index++);
                }
            };
        }
    }

}
