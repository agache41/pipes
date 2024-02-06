package io.github.agache41.ormpipes.pipes.iostream;

import io.github.agache41.ormpipes.config.Constants;
import io.github.agache41.ormpipes.functional.StrongType;
import io.github.agache41.ormpipes.functional.ThrowingFunction;
import io.github.agache41.ormpipes.pipe.AnnotablePipe;
import io.github.agache41.ormpipes.pipes.typeFile.TypeFile;

import java.io.*;

/**
 * <pre>
 * The type Io stream pipes.
 * </pre>
 */
public class IOStreamPipes {
    /**
     * <pre>
     * The type File input stream pipe.
     * </pre>
     */
    public static class FileInputStreamPipe implements AnnotablePipe<IOStream.FileBased, File, InputStream> {

        private ThrowingFunction<File, InputStream> function;

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
            return StrongType.of(InputStream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(IOStream.FileBased cfg) {
            this.function = FileInputStream::new;
            final int bufferSize = cfg.bufferSizeKB() * Constants.KB;
            if (bufferSize != 0) {
                this.function = this.function.andThen(inputStream -> new BufferedInputStream(inputStream, bufferSize));
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<File, InputStream> function() {
            return this.function;
        }
    }

    /**
     * <pre>
     * The type File output stream pipe.
     * </pre>
     */
    public static class FileOutputStreamPipe implements AnnotablePipe<IOStream.FileBased, File, OutputStream> {
        private ThrowingFunction<File, OutputStream> function;

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
            return StrongType.of(OutputStream.class);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void configure(IOStream.FileBased cfg) {
            final boolean append = cfg.append();
            this.function = file -> new FileOutputStream(file, append);
            final int bufferSize = cfg.bufferSizeKB() * Constants.KB;
            if (bufferSize != 0) {
                this.function = this.function.andThen(outputStream -> new BufferedOutputStream(outputStream, bufferSize));
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ThrowingFunction<File, OutputStream> function() {
            return this.function;
        }
    }
}
